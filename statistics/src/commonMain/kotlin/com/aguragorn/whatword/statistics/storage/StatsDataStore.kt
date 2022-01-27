package com.aguragorn.whatword.statistics.storage

import com.aguragorn.whatword.statistics.model.Stats
import com.benasher44.uuid.uuid4

class StatsDataStore {
    private val statsList = mutableListOf<Stats>()

    fun saveStats(stats: Stats): Stats {
        val toSave = if (stats.id == null) stats.copy(id = uuid4().toString()) else stats
        statsList.add(toSave)
        return toSave
    }

    fun getStatsFor(language: String, wordLength: Int): Stats? {
        return statsList
            .firstOrNull { it.language == language && it.wordLength == wordLength }
            ?.copy()
    }
}