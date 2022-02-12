package com.aguragorn.whatword.statistics.storage

import com.aguragorn.whatword.config.model.GameConfig
import com.aguragorn.whatword.statistics.model.Stats

interface StatsDataStore {
    suspend fun saveStats(stats: Stats): Stats
    suspend fun getStatsFor(gameConfig: GameConfig): Stats?
}