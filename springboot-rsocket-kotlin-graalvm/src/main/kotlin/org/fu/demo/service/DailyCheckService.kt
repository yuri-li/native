package org.fu.demo.service

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