package com.aguragorn.whatword.session.model

import com.aguragorn.whatword.config.model.GameConfig
import com.aguragorn.whatword.game.model.MysteryWord
import com.aguragorn.whatword.game.utils.asPuzzleNumber
import com.aguragorn.whatword.grid.model.Word
import com.aguragorn.whatword.keyboard.model.Letter
import com.aguragorn.whatword.utils.today
import kotlinx.datetime.LocalDate

typealias GridState = List<Word>
typealias KeyboardState = List<Letter>

class GameSession(
    val id: String? = null,
    val gameConfig: GameConfig,
    val gridState: GridState,
    val keyboardState: KeyboardState,
    val mysteryWord: MysteryWord,
) {
    val isWon: Boolean get() = gridState.last().string() == mysteryWord.word
    val isLost: Boolean get() = gridState.size == gameConfig.maxTurnCount
    val isDone: Boolean get() = isWon || isLost
    val isCurrent: Boolean get() = LocalDate.today().asPuzzleNumber() == mysteryWord.puzzleNumber
}