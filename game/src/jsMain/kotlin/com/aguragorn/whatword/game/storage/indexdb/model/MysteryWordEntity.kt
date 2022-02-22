package com.aguragorn.whatword.game.storage.indexdb.model

import com.aguragorn.whatword.game.model.MysteryWord
import com.aguragorn.whatword.indexdb.model.Entity
import kotlinext.js.jso

interface MysteryWordEntity : Entity {
    var puzzleNumber: Int
    var word: String
}

fun MysteryWord.toMysteryWordEntity(): MysteryWordEntity = jso {
    puzzleNumber = this@toMysteryWordEntity.puzzleNumber
    word = this@toMysteryWordEntity.word
}

fun MysteryWordEntity.toMysteryWord(): MysteryWord = MysteryWord(
    puzzleNumber = puzzleNumber,
    word = word
)