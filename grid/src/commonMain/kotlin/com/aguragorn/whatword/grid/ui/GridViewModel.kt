package com.aguragorn.whatword.grid.ui

import com.aguragorn.whatword.core.grid.model.Word
import com.aguragorn.whatword.core.keyboard.model.Letter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GridViewModel(
    private val wordLength: Int = 5,
    private val maxTurnCount: Int = 6,
) {
    private val _words = MutableStateFlow(listOf<Word>())
    val words: StateFlow<List<Word>> = _words.asStateFlow()

    private fun mutableWords() = _words.value.toMutableList()

    private fun updateWords(words: List<Word>) {
        _words.value = words
    }

    fun onLetterPressed(letter: Letter) {
        val words = mutableWords()

        if (words.lastOrNull()?.letters?.size == wordLength) return

        if (words.isEmpty()) words.add(Word())

        words.last().letters.add(letter)
        updateWords(words)
    }

    fun onDelete() {
        val words = mutableWords()
        words.last().letters.removeLastOrNull()
        updateWords(words)
    }

    fun onWordValidated(word: Word) {
        val words = mutableWords()
        val validatedLetters = word.letters.associateBy { it.char }

        for (letter in words.last().letters) {
            validatedLetters[letter.char]?.let { letter.status = it.status }
        }

        updateWords(words)
    }
}