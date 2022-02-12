package com.aguragorn.whatword.statistics.storage.indexdb

import com.aguragorn.whatword.config.model.GameConfig
import com.aguragorn.whatword.statistics.model.Stats
import com.aguragorn.whatword.statistics.storage.StatsDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class IndexDbStatsDataStore : StatsDataStore,
    CoroutineScope {

    override val coroutineContext = Dispatchers.Default

    override suspend fun saveStats(stats: Stats): Stats {
        TODO("Not yet implemented")
    }

    override suspend fun getStatsFor(gameConfig: GameConfig): Stats? {
        TODO("Not yet implemented")
    }
}