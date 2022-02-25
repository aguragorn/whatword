package com.aguragorn.whatword.indexdb

import com.aguragorn.whatword.indexdb.model.Entity
import com.aguragorn.whatword.indexdb.model.EntityMeta
import com.juul.indexeddb.Database
import com.juul.indexeddb.openDatabase
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

class DatabaseWrapper<T : Entity>(
    private val upgradeHelper: IndexDbUpgradeHelper,
    private val meta: EntityMeta
) {
    private var db: Database? = null

    private suspend fun getDatabase(): Database = openDatabase(
        name = upgradeHelper.databaseName,
        version = upgradeHelper.currentVersion
    ) { database, oldVersion, _ ->
        upgradeHelper.onUpgrade(this, database, oldVersion)
    }

    private suspend fun <R> useDb(block: suspend (Database) -> R): R {
        val db = db ?: getDatabase().also { db = it }
        return block(db)
    }

    suspend fun save(
        entity: T
    ): T = useDb { database ->
        database.writeTransaction(meta.tableName) {
            objectStore(meta.tableName).put(entity)
            return@writeTransaction entity
        }
        entity
    }

    suspend fun findAll(
        condition: (T) -> Boolean
    ): List<T> = useDb { database ->
        database.transaction(meta.tableName) {
            val store = objectStore(meta.tableName)
            val results = mutableListOf<T>()

            store.openCursor()
                .map { it.value as T }
                .filter(condition)
                .toList(results)
        }
    }

    suspend fun findOneOrNull(
        condition: (T) -> Boolean
    ): T? = useDb { database ->
        database.transaction(meta.tableName) {
            val store = objectStore(meta.tableName)

            store.openCursor()
                .map { it.value as T }
                .firstOrNull(condition)
        }
    }
}