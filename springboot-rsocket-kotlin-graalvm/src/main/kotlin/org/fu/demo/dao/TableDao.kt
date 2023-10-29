package org.fu.demo.dao

import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.metamodel.EntityMetamodel
import org.komapper.r2dbc.R2dbcDatabase

object TableDao {
    suspend fun createTable(db: R2dbcDatabase, vararg metamodels: EntityMetamodel<*, *, *>) = db.runQuery { QueryDsl.create(metamodels.toList()) }
}