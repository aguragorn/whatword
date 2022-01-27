package com.aguragorn.whatword.statistics.model

data class Stats(
    val id: String? = null,
    var gamesPlayed: Long = 0,
    var language: String,
    var wordLength: Int,
    var wins: Long = 0,
    var currentStreak: Int = 0,
    var maxStreak: Int = 0,
    var bestTimeMs: Long = 0,
    var roundsStats: MutableList<RoundsStat> = mutableListOf()
)

data class RoundsStat(
    var guessCount: Int,
    var numberOfGames: Long,
)

