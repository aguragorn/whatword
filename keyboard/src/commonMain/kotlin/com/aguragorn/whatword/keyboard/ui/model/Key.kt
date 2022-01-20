package com.aguragorn.whatword.keyboard.ui.model

class Key(
    val char: Char,
    var status: Status = Status.UNKNOWN,
) {
    enum class Status {
        CORRECT, MISPLACED, INCORRECT, UNKNOWN
    }
}