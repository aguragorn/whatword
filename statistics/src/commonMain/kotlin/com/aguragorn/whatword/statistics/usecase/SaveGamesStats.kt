package com.aguragorn.whatword.statistics.usecase

import com.aguragorn.whatword.config.model.GameConfig
import com.aguragorn.whatword.statistics.model.RoundsStat
import com.aguragorn.whatword.statistics.model.Stats
import com.aguragorn.whatword.statistics.storage.StatsDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.time.Duration

class SaveGamesStats(
    private val statsStore: StatsDataStore
) : CoroutineScope {

    override val coroutineContext = Dispatchers.Default

    suspend operator fun invoke(
        config: GameConfig,
        isWon: Boolean,
        time: Duration,
        rounds: Int,
        mysteryWord: String,
    ): Stats = withContext(coroutineContext) {
        val stats = statsStore.getStatsFor(gameConfig = config)
            ?.also {
                it.gamesPlayed += 1L
                it.lastMysteryWord = mysteryWord
            }
            ?: Stats(
                gameConfig = GameConfig.default,
                gamesPlayed = 1L,
                lastMysteryWord = mysteryWord,
            )

        if (isWon) {
            stats.wins += 1L
            stats.currentStreak += 1L
            stats.roundsStats
                .firstOrNull { it.guessCount == rounds }
                ?.apply { numberOfGames += 1L }
                ?: RoundsStat(
                    guessCount = rounds,
                    numberOfGames = 1L
                ).also { stats.roundsStats.add(it) }

            if (stats.currentStreak > stats.bestStreak) {
                stats.bestStreak = stats.currentStreak
            }

            if (stats.bestTimeMs == 0L || time.inWholeMilliseconds < stats.bestTimeMs) {
                stats.bestTimeMs = time.inWholeMilliseconds
            }

        } else {
            stats.currentStreak = 0L
        }

        println(stats.toString())
        return@withContext statsStore.saveStats(stats)
    }
}