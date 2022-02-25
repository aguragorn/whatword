package com.aguragorn.whatword.statistics.storage.indexdb

import com.aguragorn.whatword.config.indexdb.model.toGameConfig
import com.aguragorn.whatword.config.model.GameConfig
import com.aguragorn.whatword.indexdb.DatabaseWrapper
import com.aguragorn.whatword.statistics.model.Stats
import com.aguragorn.whatword.statistics.storage.StatsDataStore
import com.aguragorn.whatword.statistics.storage.indexdb.model.StatsEntity
import com.aguragorn.whatword.statistics.storage.indexdb.model.toDomain
import com.aguragorn.whatword.statistics.storage.indexdb.model.toEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IndexDbStatsDataStore(
    private val statsDB: DatabaseWrapper<StatsEntity>
) : StatsDataStore,
    CoroutineScope {

    override val coroutineContext = Dispatchers.Default

    override suspend fun saveStats(
        stats: Stats
    ): Stats = withContext(coroutineContext) {
        return@withContext statsDB
            .save(stats.toEntity())
            .toDomain()
    }

    override suspend fun getStatsFor(
        gameConfig: GameConfig
    ): Stats? = withContext(coroutineContext) {
        return@withContext statsDB
            .findOneOrNull { it.gameConfig.toGameConfig() == gameConfig }
            ?.toDomain()
    }
}