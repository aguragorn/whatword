package com.aguragorn.whatword.core.keyboard.model

class Letter(
    val char: Char,
    var status: Status = Status.UNKNOWN,
) {
    enum class Status {
        CORRECT, MISPLACED, INCORRECT, UNKNOWN
    }
}