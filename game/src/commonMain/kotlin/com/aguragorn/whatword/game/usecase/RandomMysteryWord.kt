package com.aguragorn.whatword.game.usecase

import com.aguragorn.whatword.config.model.GameConfig
import com.aguragorn.whatword.game.model.MysteryWord
import com.aguragorn.whatword.game.storage.MysteryWordStorage
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus

class RandomMysteryWord(
    private val mysteryWordStorage: MysteryWordStorage
) {
    suspend operator fun invoke(
        config: GameConfig,
        date: LocalDate? = null
    ): MysteryWord {
        val mysteryWords = mysteryWordStorage.getMysteryWords(config)

        if (date == null) {
            return MysteryWord(0, mysteryWords.random())
        }

        val dayZero = LocalDate(2022, 2, 15)
        val puzzleNumber = date.minus(dayZero).days
        val word = mysteryWords[puzzleNumber % mysteryWords.size]

        return MysteryWord(puzzleNumber, word)
    }
}