package com.aguragorn.whatword.statistics.storage

import com.aguragorn.whatword.config.model.GameConfig
import com.aguragorn.whatword.statistics.model.Stats
import com.benasher44.uuid.uuid4
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StatsDataStore : CoroutineScope {
    override val coroutineContext = Dispatchers.Default

    private val statsList = mutableListOf<Stats>()

    suspend fun saveStats(
        stats: Stats
    ): Stats = withContext(coroutineContext) {
        // TODO: Actually persist stats
        val toSave = if (stats.id == null) stats.copy(id = uuid4().toString()) else stats
        statsList.add(toSave)
        return@withContext toSave
    }

    suspend fun getStatsFor(
        gameConfig: GameConfig
    ): Stats? = withContext(coroutineContext) {

        return@withContext statsList
            .firstOrNull { it.gameConfig == gameConfig }
            ?.copy()
    }

}