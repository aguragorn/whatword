package com.aguragorn.whatword.keyboard.model

import com.aguragorn.whatword.core.keyboard.model.Letter

class KeyLayout(
    var letters: List<List<Letter>>
)

fun QwertyLayout(): KeyLayout = KeyLayout(
    listOf(
        "quertyuiop".map { Letter(it) },
        "asdfghjkl".map { Letter(it) },
        "${Letter.enterChar}zxcvbnm${Letter.deleteChar}".map { Letter(it) }
    )
)

