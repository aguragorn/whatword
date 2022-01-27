package com.aguragorn.whatword.grid.model

import com.aguragorn.whatword.keyboard.model.Letter

data class Word(
    val letters: MutableList<Letter> = mutableListOf()
) {
    fun copy(): Word = Word(letters.map { it.copy() }.toMutableList())
}