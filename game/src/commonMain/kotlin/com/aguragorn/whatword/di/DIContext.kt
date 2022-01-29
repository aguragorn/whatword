package com.aguragorn.whatword.di

import com.aguragorn.whatword.game.storage.MysteryWordDataStore
import com.aguragorn.whatword.game.usecase.RandomMysteryWord
import com.aguragorn.whatword.validator.usecase.ValidateWord
import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val gameDI: DirectDI = DI.direct {
    bindSingleton { MysteryWordDataStore() }
    bindSingleton { ValidateWord(mysteryWordDataStore = instance()) }
    bindSingleton { RandomMysteryWord(mysteryWordDataStore = instance()) }
}