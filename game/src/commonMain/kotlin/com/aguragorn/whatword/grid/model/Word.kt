package com.aguragorn.whatword.grid.model

import com.aguragorn.whatword.keyboard.model.Letter
import kotlinx.serialization.Serializable

@Serializable
data class Word(
    val letters: MutableList<Letter> = mutableListOf()
) {
    fun string(): String = letters.joinToString(separator = "")
    fun copy(): Word = Word(letters.map { it.copy() }.toMutableList())
}