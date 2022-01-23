package com.aguragorn.whatword.keyboard.model

import com.aguragorn.whatword.core.keyboard.model.Letter

class KeyLayout(
    var letters: List<List<Letter>>
)

fun QwertyLayout(): KeyLayout = KeyLayout(
    listOf(
        "QWERTYUIOP".map { Letter(it) },
        "ASDFGHJKL".map { Letter(it) },
        "${Letter.enterChar}ZXCVBNM${Letter.deleteChar}".map { Letter(it) }
    )
)

