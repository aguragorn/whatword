package com.aguragorn.whatword.config.model

data class GameConfig(
    val language: String,
    val wordLength: Int,
    val maxTurnCount: Int,
) {
    companion object {
        val default = GameConfig(
            language = "english",
            wordLength = 5,
            maxTurnCount = 6
        )
    }
}