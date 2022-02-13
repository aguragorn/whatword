package com.aguragorn.whatword.statistics.storage.indexdb

import com.aguragorn.whatword.statistics.storage.indexdb.model.*
import com.juul.indexeddb.*

class IndexDbFactory {

    suspend fun <T : Entity> getDatabase(
        databaseName: String = "default",
        meta: EntityMeta,
    ): DatabaseWrapper<T> = DatabaseWrapper(
        database = openDatabase(
            name = databaseName,
            version = currentVersion
        ) { database, oldVersion, _ ->
            if (oldVersion < 1) createStatsStore(database, meta)
        },
        meta = meta
    )


    companion object {
        private const val v_1_0_0 = 1_00_000
        const val currentVersion = v_1_0_0
    }
}

fun VersionChangeTransaction.createStatsStore(database: Database, meta: EntityMeta) {
    val store = database.createObjectStore(
        meta.tableName,
        KeyPath(StatsEntity::id.name)
    )

    createNonUniqueIndex(store, StatsEntityMeta.bestTimeMs)
    createNonUniqueIndex(store, StatsEntityMeta.currentStreak)
    createNonUniqueIndex(store, StatsEntityMeta.gameConfig)
    createNonUniqueIndex(store, StatsEntityMeta.gamesPlayed)
    createNonUniqueIndex(store, StatsEntityMeta.lastMysteryWord)
    createNonUniqueIndex(store, StatsEntityMeta.maxStreak)
    createNonUniqueIndex(store, StatsEntityMeta.roundsStats)
    createNonUniqueIndex(store, StatsEntityMeta.wins)
}

fun VersionChangeTransaction.createNonUniqueIndex(store: ObjectStore, fieldName: String) {
    store.createIndex(fieldName, fieldName.keyPath, unique = false)
}