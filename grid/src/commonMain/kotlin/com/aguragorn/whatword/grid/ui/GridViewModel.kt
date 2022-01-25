package com.aguragorn.whatword.grid.ui

import com.aguragorn.whatword.core.grid.model.Word
import com.aguragorn.whatword.core.keyboard.model.Letter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GridViewModel(
    val wordLength: Int = 5,
    val maxTurnCount: Int = 6,
) {
    private val _words = MutableStateFlow(listOf<Word>())
    val words: StateFlow<List<Word>> = _words.asStateFlow()

    val lastWord: Word get() = words.value.lastOrNull()?.copy() ?: Word()

    private fun mutableWords() = _words.value.map { it.copy() }.toMutableList()

    private fun updateWords(words: List<Word>) {
        _words.value = words
    }

    fun addLetterToGrid(letter: Letter) {
        if (lastWord.letters.size == wordLength) return

        val words = mutableWords()
        if (words.isEmpty()) words.add(Word())

        words.last().letters.add(letter)
        updateWords(words)
    }

    fun deleteLastLetter() {
        if (lastWord.letters.isEmpty()) return
        val words = mutableWords()
        words.last().letters.removeLastOrNull()
        updateWords(words)
    }

    fun onWordValidated(word: Word) {
        val words = mutableWords()
        val validatedLetters = word.letters

        for ((i, letter) in words.last().letters.withIndex()) {
            letter.status = validatedLetters[i].status
        }

        updateWords(words)
    }
}