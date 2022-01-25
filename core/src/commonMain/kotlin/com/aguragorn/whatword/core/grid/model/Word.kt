package com.aguragorn.whatword.core.grid.model

import com.aguragorn.whatword.core.keyboard.model.Letter

data class Word(
    val letters: MutableList<Letter> = mutableListOf()
) {
    fun copy(): Word = Word(letters.map { it.copy() }.toMutableList())
}