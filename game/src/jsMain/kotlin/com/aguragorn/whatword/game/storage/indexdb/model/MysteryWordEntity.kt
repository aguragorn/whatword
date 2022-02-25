package com.aguragorn.whatword.game.storage.indexdb.model

import com.aguragorn.whatword.game.model.MysteryWord
import com.aguragorn.whatword.indexdb.model.Entity
import kotlinext.js.jso

open class MysteryWordEntity : Entity {
    override var id: String? = null
    open var puzzleNumber: Int = 0
    open var word: String = ""
}

fun MysteryWord.toMysteryWordEntity(): MysteryWordEntity {
    val model = this

    return jso {
        puzzleNumber = model.puzzleNumber
        word = model.word
    }
}

fun MysteryWordEntity.toMysteryWord(): MysteryWord = MysteryWord(
    puzzleNumber = puzzleNumber,
    word = word
)