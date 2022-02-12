package com.aguragorn.whatword.statistics.model

import com.aguragorn.whatword.config.model.GameConfig
import kotlinx.serialization.Serializable

data class Stats constructor(
    val id: String? = null,
    var gamesPlayed: Long = 0,
    var gameConfig: GameConfig,
    var wins: Long = 0,
    var currentStreak: Long = 0,
    var maxStreak: Long = 0,
    var bestTimeMs: Long = 0,
    var roundsStats: MutableList<RoundsStat> = mutableListOf(),
    var lastMysteryWord: String,
) {
    val isLastRoundWon: Boolean get() = currentStreak != 0L
    val hasPlayed: Boolean get() = gamesPlayed > 0
    val winRate: Double = (wins.toDouble() / gamesPlayed.toDouble()) * 100.0
}

@Serializable
data class RoundsStat(
    var guessCount: Int,
    var numberOfGames: Long,
)

fun List<RoundsStat>.roundWithMostWins() = maxByOrNull { it.numberOfGames }

