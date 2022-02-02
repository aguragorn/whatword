package com.aguragorn.whatword.validator.usecase

import com.aguragorn.whatword.config.model.GameConfig
import com.aguragorn.whatword.game.storage.MysteryWordDataStore
import com.aguragorn.whatword.grid.model.Word
import com.aguragorn.whatword.keyboard.model.Letter
import com.aguragorn.whatword.validator.model.IncorrectLengthException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ValidateWord(
    private var mysteryWordDataStore: MysteryWordDataStore,
) : CoroutineScope {
    override val coroutineContext = Dispatchers.Default

    suspend operator fun invoke(
        attempt: Word,
        mysteryWord: String,
        config: GameConfig,
    ): Word = withContext(coroutineContext) {
        val mysteryLetters = mysteryWord.uppercase().toList()
        val wordLength = mysteryWord.length

        if (attempt.letters.size != wordLength) {
            throw IncorrectLengthException(expectedLength = wordLength)
        }

        val attemptStr = attempt.letters.joinToString(separator = "") { "${it.char}" }
        val validWords = mysteryWordDataStore.getMysteryWords(gameConfig = config)

        if (attemptStr !in validWords) {
            throw IllegalArgumentException("$attemptStr is not a valid word.")
        }

        val validatedWord = Word(attempt.letters.toMutableList())
        for ((i, letter) in validatedWord.letters.withIndex()) {
            val letterChar = letter.char.uppercaseChar()

            val exists = letterChar in mysteryLetters
            val correctPosition = letterChar == mysteryLetters[i]

            letter.status = when {
                exists && correctPosition -> Letter.Status.CORRECT
                exists -> Letter.Status.MISPLACED
                else -> Letter.Status.INCORRECT
            }
        }

        return@withContext validatedWord
    }
}