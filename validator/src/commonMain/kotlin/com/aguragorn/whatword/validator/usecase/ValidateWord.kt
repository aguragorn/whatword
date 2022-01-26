package com.aguragorn.whatword.validator.usecase

import com.aguragorn.whatword.core.grid.model.Word
import com.aguragorn.whatword.core.keyboard.model.Letter
import com.aguragorn.whatword.validator.model.IncorrectLengthException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ValidateWord(
    private var wordLength: Int = 5,
    private var mysteryWord: String = "rated"
) : CoroutineScope {
    override val coroutineContext = Dispatchers.Default

    // TODO: pull mystery word from list

    suspend operator fun invoke(
        attempt: Word
    ): Word = withContext(coroutineContext) {
        val mysteryLetters = mysteryWord.uppercase().toList()

        if (attempt.letters.size != mysteryWord.length) {
            throw IncorrectLengthException(
                expectedLength = mysteryWord.length
            )
        }
        // TODO: check if attempt is a valid word

        val validatedWord = Word(attempt.letters.toMutableList())
        for ((i, letter) in validatedWord.letters.withIndex()) {
            val exists = letter.char.uppercaseChar() in mysteryLetters
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