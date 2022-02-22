package com.aguragorn.whatword.statistics.storage.indexdb

import com.aguragorn.whatword.indexdb.IndexDbUpgradeHelper
import com.aguragorn.whatword.indexdb.createNonUniqueIndex
import com.aguragorn.whatword.indexdb.keyPath
import com.aguragorn.whatword.statistics.storage.indexdb.model.StatsEntityMeta
import com.juul.indexeddb.Database
import com.juul.indexeddb.VersionChangeTransaction

class StatsDbUpgradeHelper : IndexDbUpgradeHelper {

    override val databaseName: String = "StatisticsDB"
    override val currentVersion: Int = StatsDbUpgradeHelper.currentVersion
    override val onUpgrade = fun VersionChangeTransaction.(
        database: Database, oldVersion: Int
    ) {
        if (oldVersion < v_1_0_0) {
            val store = database.createObjectStore(
                StatsEntityMeta.tableName,
                StatsEntityMeta.id.keyPath
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
    }

    companion object {
        private const val v_1_0_0 = 1_00_000
        const val currentVersion = v_1_0_0
    }
}