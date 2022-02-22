package com.aguragorn.whatword.storage.indexdb

import com.aguragorn.whatword.indexdb.IndexDbUpgradeHelper
import com.aguragorn.whatword.indexdb.createNonUniqueIndex
import com.aguragorn.whatword.indexdb.keyPath
import com.aguragorn.whatword.session.storage.indexdb.model.GameSessionEntityMeta
import com.aguragorn.whatword.statistics.storage.indexdb.model.StatsEntityMeta
import com.juul.indexeddb.Database
import com.juul.indexeddb.VersionChangeTransaction

class GameDbUpgradeHelper : IndexDbUpgradeHelper {

    override val databaseName: String = "GameDB"
    override val currentVersion: Int = GameDbUpgradeHelper.currentVersion
    override val onUpgrade = fun VersionChangeTransaction.(
        database: Database, oldVersion: Int
    ) {
        if (oldVersion < v1_0_0) {
            val store = database.createObjectStore(
                GameSessionEntityMeta.tableName,
                StatsEntityMeta.id.keyPath
            )

            createNonUniqueIndex(store, GameSessionEntityMeta.gameConfig)
            createNonUniqueIndex(store, GameSessionEntityMeta.gridState)
            createNonUniqueIndex(store, GameSessionEntityMeta.keyboardState)
            createNonUniqueIndex(store, GameSessionEntityMeta.mysteryWord)
        }
    }

    companion object {
        const val v1_0_0 = 1_00_000
        const val currentVersion = v1_0_0
    }
}