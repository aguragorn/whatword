package com.aguragorn.whatword.grid.ui

import com.aguragorn.whatword.grid.model.Word
import com.aguragorn.whatword.keyboard.model.Letter
import com.aguragorn.whatword.session.model.GridState
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

    fun newWord() {
        updateWords(mutableWords().apply { add(Word()) })
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

    fun load(words: GridState) {
        updateWords(words)
    }
}