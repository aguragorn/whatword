package com.aguragorn.whatword.statistics.usecase

import com.aguragorn.whatword.statistics.model.RoundsStat
import com.aguragorn.whatword.statistics.model.Stats
import com.aguragorn.whatword.statistics.storage.StatsDataStore
import com.benasher44.uuid.uuid4
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.time.Duration

class SaveGamesStats(
    private val statsStore: StatsDataStore
) : CoroutineScope {

    override val coroutineContext = Dispatchers.Default

    suspend operator fun invoke(
        language: String,
        wordLength: Int,
        isWon: Boolean,
        time: Duration,
        rounds: Int,
        mysteryWord: String,
    ): Stats = withContext(coroutineContext) {
        val stats = statsStore
            .getStatsFor(language = language, wordLength = wordLength)
            ?: Stats(
                id = uuid4().toString(),
                language = language,
                wordLength = wordLength,
                lastMysteryWord = mysteryWord
            )

        stats.gamesPlayed += 1

        if (isWon) {
            stats.wins += 1
            stats.currentStreak += 1
            stats.roundsStats
                .firstOrNull { it.guessCount == rounds }
                ?.apply { numberOfGames += 1 }
                ?: RoundsStat(
                    guessCount = rounds,
                    numberOfGames = 1
                ).also { stats.roundsStats.add(it) }

            if (stats.currentStreak > stats.maxStreak) {
                stats.maxStreak = stats.currentStreak
            }

            if (stats.bestTimeMs == 0L || time.inWholeMilliseconds < stats.bestTimeMs) {
                stats.bestTimeMs = time.inWholeMilliseconds
            }

        } else {
            stats.currentStreak = 0
        }

        println(stats.toString())
        return@withContext statsStore.saveStats(stats)
    }
}