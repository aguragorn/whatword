package com.aguragorn.whatword.di

import com.aguragorn.whatword.validator.usecase.ValidateWord
import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.bindFactory

val gameDI: DirectDI = DI.direct {
    bindFactory { wordLength: Int -> ValidateWord(wordLength) }
}