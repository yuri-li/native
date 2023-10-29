# 1 简介

## 1.1 场景描述

实现简化版的日常签到(DailyCheck)

- 建表，t_daily_check_event(id, username, createdAt)

- 插入若干条数据

- 根据username分组统计总共insert了多少条

## 1.2 目的

**1)**  komapper ORM映射

**2)** insert

**3)** 复杂查询

**4)** 观察kotlin coroutines + r2dbc的性能

# 2 model

```kotlin
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.komapper.annotation.KomapperCreatedAt
import org.komapper.annotation.KomapperEntityDef
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable

@Serializable
data class DailyCheckEvent(
    val id: String,
    val username: String,
    val createdAt: LocalDateTime? = null,
)

@KomapperEntityDef(DailyCheckEvent::class, aliases = ["dailyCheckEvent"])
@KomapperTable("t_daily_check_event")
data class DalilyCheckEventDef(
    @KomapperId
    val id: Nothing,
    @KomapperCreatedAt
    val createdAt: Nothing,
)
```

# 3 Controller

```kotlin
import org.fu.demo.service.DailyCheckService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller

@Controller
class DailyCheckController(val service: DailyCheckService) {
    @MessageMapping("anonymous.check.initDB")
    suspend fun initDB() = service.initDB()

    @MessageMapping("anonymous.check.clockIn")
    suspend fun clockIn(username: String) = service.clockIn(username)

    @MessageMapping("anonymous.check.count")
    suspend fun count(list: List<String>) = service.count(list)

}
```

# 4 Service

```kotlin
import org.fu.demo.dao.BaseDao
import org.fu.demo.dao.TableDao
import org.fu.demo.model.DailyCheckEvent
import org.fu.demo.model.dailyCheckEvent
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.operator.count
import org.komapper.r2dbc.R2dbcDatabase
import org.springframework.stereotype.Service
import java.util.*

@Service
class DailyCheckService(val db: R2dbcDatabase) {
    private val eventMeta = Meta.dailyCheckEvent
    private val eventDao = BaseDao(db, eventMeta)
    suspend fun initDB() {
        TableDao.createTable(db, eventMeta)
    }

    suspend fun clockIn(username: String) {
        eventDao.save(DailyCheckEvent(UUID.randomUUID().toString(), username))
    }

    suspend fun count(list: List<String>): String {
        val result = db.runQuery {
            QueryDsl.from(eventMeta)
                .where { eventMeta.username inList list }
                .groupBy(eventMeta.username)
                .select(eventMeta.username, count(eventMeta.id))
        }
        return result.map { "username ${it.first} 签到 ${it.second} 次" }.joinToString()
    }
}
```

# 5 测试

```kotlin
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.longs.shouldBeLessThan
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.retrieveMono
import kotlin.time.measureTime

@SpringBootTest
class DailyCheckSpec(val requester: RSocketRequester) : StringSpec({
    "为什么要🔒" {
        val time = measureTime {
            requester.route("anonymous.check.initDB")
                .send()
                .awaitSingleOrNull()
            val users = listOf("yuri", "alice", "kikk")
            users.map { username ->
                (1..100).map {
                    launch {
                        requester.route("anonymous.check.clockIn")
                            .data(username)
                            .send()
                            .awaitSingleOrNull()
                    }
                }
            }.flatten().joinAll()
            val msg = requester.route("anonymous.check.count")
                .data(Json.encodeToString(users))
                .retrieveMono<String>()
                .awaitSingle()
            log.info(msg)
        }
        log.info("耗时: $time")
        time.inWholeSeconds.shouldBeLessThan(1)
    }
}) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}
```


