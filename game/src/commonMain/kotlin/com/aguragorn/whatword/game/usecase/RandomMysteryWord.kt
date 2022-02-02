package com.aguragorn.whatword.game.usecase

import com.aguragorn.whatword.config.model.GameConfig
import com.aguragorn.whatword.game.storage.MysteryWordDataStore
import kotlinx.datetime.LocalDate

class RandomMysteryWord(
    private val mysteryWordDataStore: MysteryWordDataStore
) {

    suspend operator fun invoke(
        config: GameConfig,
        date: LocalDate? = null
    ): String {
        // TODO: Daily random word
        return mysteryWordDataStore.getMysteryWords(config).random()
    }
}