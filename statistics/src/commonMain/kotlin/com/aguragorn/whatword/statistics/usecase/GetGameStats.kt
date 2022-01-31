package com.aguragorn.whatword.statistics.usecase

import com.aguragorn.whatword.statistics.model.Stats
import com.aguragorn.whatword.statistics.storage.StatsDataStore
import com.benasher44.uuid.uuid4

class GetGameStats(
    private val statsStore: StatsDataStore
) {
    suspend operator fun invoke(
        language: String,
        wordLength: Int,
    ): Stats {
        return statsStore.getStatsFor(
            language = language,
            wordLength = wordLength
        ) ?: Stats(
            id = uuid4().toString(),
            language = language,
            wordLength = wordLength,
            lastMysteryWord = ""
        )
    }
}