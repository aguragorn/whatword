package com.aguragorn.whatword.keyboard.model

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

    enum class Status(val weight: Int) {
        CORRECT(3),
        MISPLACED(2),
        INCORRECT(3),
        UNKNOWN(0);
    }

}