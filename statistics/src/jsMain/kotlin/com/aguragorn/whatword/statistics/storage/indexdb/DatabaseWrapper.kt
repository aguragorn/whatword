package com.aguragorn.whatword.statistics.storage.indexdb

import com.aguragorn.whatword.statistics.storage.indexdb.model.Entity
import com.aguragorn.whatword.statistics.storage.indexdb.model.EntityMeta
import com.juul.indexeddb.Database
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

class DatabaseWrapper<T : Entity>(
    private val database: Database,
    private val meta: EntityMeta
) {
    suspend fun save(
        entity: T
    ): T = database.writeTransaction(meta.tableName) {
        objectStore(meta.tableName).put(entity)
        return@writeTransaction entity
    }

    suspend fun findAll(
        condition: (T) -> Boolean
    ): List<T> = database.transaction(meta.tableName) {
        val store = objectStore(meta.tableName)
        val results = mutableListOf<T>()

        store.openCursor()
            .map { it.value as T }
            .filter(condition)
            .toList(results)
    }

    suspend fun findOneOrNull(
        condition: (T) -> Boolean
    ): T? = database.transaction(meta.tableName) {
        val store = objectStore(meta.tableName)

        store.openCursor()
            .map { it.value as T }
            .firstOrNull(condition)
    }
}