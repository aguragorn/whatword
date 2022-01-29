package com.aguragorn.whatword.game.usecase

import com.aguragorn.whatword.game.storage.MysteryWordDataStore
import kotlinx.datetime.LocalDate

class RandomMysteryWord(
    private val mysteryWordDataStore: MysteryWordDataStore
) {

    suspend operator fun invoke(
        language: String,
        wordLength: Int,
        date: LocalDate? = null
    ): String {
        // TODO: Daily random word
        return mysteryWordDataStore.getMysteryWords(language, wordLength).random()
    }
}