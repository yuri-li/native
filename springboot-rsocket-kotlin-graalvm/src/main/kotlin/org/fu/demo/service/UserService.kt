package org.fu.demo.service

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.toKotlinLocalDateTime
import org.fu.demo.dao.BaseDao
import org.fu.demo.dao.TableDao
import org.fu.demo.model.Gender
import org.fu.demo.model.User
import org.fu.demo.model.user
import org.komapper.core.dsl.Meta
import org.komapper.r2dbc.R2dbcDatabase
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserService(val db: R2dbcDatabase) {
    private val meta = Meta.user
    private val dao = BaseDao(db, meta)

    private fun buildUser(id: String) = User(
        id,
        age = 18,
        gender = Gender.Male,
        time = LocalDateTime.now().toKotlinLocalDateTime()
    )

    suspend fun initDB() {
        db.withTransaction {
            TableDao.createTable(db, meta)
            dao.batchSave(
                listOf("a", "b", "c").map { buildUser(it) }
            )
        }
    }

    suspend fun add(): Unit {
        dao.save(buildUser("d"))
    }

    suspend fun findAll(): Flow<User> = dao.findAll()
}