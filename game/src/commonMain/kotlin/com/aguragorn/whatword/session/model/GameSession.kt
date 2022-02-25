package com.aguragorn.whatword.session.model

import com.aguragorn.whatword.config.model.GameConfig
import com.aguragorn.whatword.game.model.MysteryWord
import com.aguragorn.whatword.grid.model.Word
import com.aguragorn.whatword.keyboard.model.Letter

typealias GridState = List<Word>

fun GridState(): GridState {
    return emptyList()
}

typealias KeyboardState = List<Letter>

fun KeyboardState(): KeyboardState {
    return emptyList()
}

class GameSession(
    val id: String? = null,
    val gameConfig: GameConfig,
    var gridState: GridState = GridState(),
    var keyboardState: KeyboardState = KeyboardState(),
    var mysteryWord: MysteryWord = MysteryWord(0, ""),
) {
    val isStub: Boolean
        get() = gridState.isEmpty()
                || keyboardState.isEmpty()
                || mysteryWord.word.isEmpty()
}