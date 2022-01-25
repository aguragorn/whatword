package com.aguragorn.whatword.core.keyboard.model

data class Letter(
    val char: Char,
    var status: Status = Status.UNKNOWN,
) {
    val isControlChar: Boolean get() = char == deleteChar || char == enterChar

    fun copy(): Letter = Letter(char, status)

    companion object {
        const val deleteChar: Char = '⌫'
        const val enterChar: Char = '⏎'
    }

    enum class Status {
        CORRECT, MISPLACED, INCORRECT, UNKNOWN
    }

}