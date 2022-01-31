package com.aguragorn.whatword.di

import com.aguragorn.whatword.game.storage.MysteryWordDataStore
import com.aguragorn.whatword.game.ui.GameViewModel
import com.aguragorn.whatword.game.usecase.RandomMysteryWord
import com.aguragorn.whatword.statistics.di.statsDi
import com.aguragorn.whatword.toaster.ToasterViewModel
import com.aguragorn.whatword.validator.usecase.ValidateWord
import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val gameDI: DirectDI = DI.direct {
    bindMysteryWord()
    bindValidator()
    bindToaster()
    bindGame()
}

private fun DI.MainBuilder.bindMysteryWord() {
    bindSingleton { MysteryWordDataStore() }
}

private fun DI.MainBuilder.bindValidator() {
    bindSingleton { ValidateWord(mysteryWordDataStore = instance()) }
}

private fun DI.MainBuilder.bindToaster() {
    bindSingleton { ToasterViewModel() }
}

private fun DI.MainBuilder.bindGame() {
    bindSingleton { RandomMysteryWord(mysteryWordDataStore = instance()) }
    bindSingleton {
        GameViewModel(
            validate = gameDI.instance(),
            randomMysteryWord = gameDI.instance(),
            saveGameStats = statsDi.instance(),
            toaster = gameDI.instance(),
            statsViewModel = statsDi.instance()
        )
    }
}