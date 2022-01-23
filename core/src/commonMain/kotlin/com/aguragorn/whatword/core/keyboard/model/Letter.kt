package com.aguragorn.whatword.core.keyboard.model

class Letter(
    val char: Char,
    var status: Status = Status.UNKNOWN,
) {
    val isControlChar: Boolean get() = char == deleteChar || char == enterChar

    enum class Status {
        CORRECT, MISPLACED, INCORRECT, UNKNOWN
    }

    companion object {
        const val deleteChar: Char = '⌫'
        const val enterChar: Char = '⏎'
    }
}