package com.aguragorn.whatword.statistics.model

import com.aguragorn.whatword.config.model.GameConfig
import kotlinx.serialization.Serializable

data class Stats(
    val id: String? = null,
    var gamesPlayed: Long = 0L,
    var gameConfig: GameConfig,
    var wins: Long = 0L,
    var currentStreak: Long = 0L,
    var bestStreak: Long = 0L,
    var bestTimeMs: Long = 0L,
    var roundsStats: MutableList<RoundsStat> = mutableListOf(),
    var lastMysteryWord: String,
) {
    val isLastRoundWon: Boolean get() = currentStreak != 0L
    val hasPlayed: Boolean get() = gamesPlayed > 0.0
    val winRate: Double
        get() = gamesPlayed.takeIf { it > 0 }?.let { (wins.toDouble() / it.toDouble()) * 100.0 } ?: 0.0
}

@Serializable
data class RoundsStat(
    var guessCount: Int,
    var numberOfGames: Long,
)

fun List<RoundsStat>.roundWithMostWins() = maxByOrNull { it.numberOfGames }

