package com.aguragorn.whatword.statistics.usecase

import com.aguragorn.whatword.config.model.GameConfig
import com.aguragorn.whatword.statistics.model.Stats
import com.aguragorn.whatword.statistics.storage.StatsDataStore
import com.benasher44.uuid.uuid4

class GetGameStats(
    private val statsStore: StatsDataStore
) {
    suspend operator fun invoke(config: GameConfig): Stats {
        return statsStore.getStatsFor(gameConfig = config) ?: Stats(
            id = uuid4().toString(),
            gameConfig = GameConfig.default,
            lastMysteryWord = ""
        )
    }
}