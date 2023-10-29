package org.fu.demo.dao

import kotlinx.coroutines.flow.Flow
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.expression.WhereDeclaration
import org.komapper.core.dsl.metamodel.EntityMetamodel
import org.komapper.core.dsl.query.first
import org.komapper.core.dsl.query.firstOrNull
import org.komapper.r2dbc.R2dbcDatabase

class BaseDao<ENTITY : Any, ID : Any, META : EntityMetamodel<ENTITY, ID, META>>(
    private val db: R2dbcDatabase,
    private val meta: META,
) {
    suspend fun save(entity: ENTITY) = db.runQuery {
        QueryDsl.insert(meta).single(entity)
    }

    suspend fun findAll() = db.flowQuery {
        QueryDsl.from(meta)
    }
    suspend fun batchSaveByFlow(flow: Flow<ENTITY>, size: Int = 10000) {
        val list = ArrayList<ENTITY>(size)
        flow.collect { value ->
            list.add(value)
            if (list.size == size) {
                batchSave(list)
                list.clear()
            }
        }
        if (list.isNotEmpty()) {
            batchSave(list)
            list.clear()
        }
    }

    /**
     * insert into Table ([ Columns ]) values (?, ?, ?), (?, ?, ?), (?, ?, ?)
     */
    suspend fun batchSave(list: List<ENTITY>) = db.runQuery { QueryDsl.insert(meta).multiple(list) }

    suspend fun find(declaration: WhereDeclaration) = db.runQuery { QueryDsl.from(meta).where(declaration).firstOrNull() }

    suspend fun <ENTITY : Any, ID : Any, META : EntityMetamodel<ENTITY, ID, META>> delete(
        metamodel: META, entity: ENTITY
    ) = db.runQuery { QueryDsl.delete(metamodel).single(entity) }

    suspend fun update(entity: ENTITY) = db.runQuery{
        QueryDsl.update(meta).single(entity)
    }
}