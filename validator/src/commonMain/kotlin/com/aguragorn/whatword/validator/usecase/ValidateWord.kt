package com.aguragorn.whatword.validator.usecase

import com.aguragorn.whatword.core.grid.model.Word
import com.aguragorn.whatword.core.keyboard.model.Letter
import com.aguragorn.whatword.validator.model.IncorrectLengthException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ValidateWord : CoroutineScope {
    override val coroutineContext = Dispatchers.Default

    suspend operator fun invoke(
        attempt: Word,
        mysteryWord: String
    ): Word = withContext(coroutineContext) {
        val mysteryLetters = mysteryWord.toList()

        if (attempt.letters.size != mysteryWord.length) {
            throw IncorrectLengthException(
                expectedLength = mysteryWord.length
            )
        }

        val validatedWord = Word(attempt.letters.toMutableList())
        for ((i, letter) in validatedWord.letters.withIndex()) {
            val exists = letter.char in mysteryLetters
            val correctPosition = letter.char == mysteryLetters[i]

            letter.status = when {
                exists && correctPosition -> Letter.Status.CORRECT
                exists -> Letter.Status.MISPLACED
                else -> Letter.Status.INCORRECT
            }
        }

        return@withContext validatedWord
    }
}