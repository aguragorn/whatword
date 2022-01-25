package com.aguragorn.whatword.game.ui

import com.aguragorn.whatword.core.keyboard.model.Letter
import com.aguragorn.whatword.grid.ui.GridViewModel
import com.aguragorn.whatword.keyboard.model.Event.KeyTapped
import com.aguragorn.whatword.keyboard.model.KeyLayout
import com.aguragorn.whatword.keyboard.model.QwertyLayout
import com.aguragorn.whatword.keyboard.ui.KeyboardViewModel
import com.aguragorn.whatword.validator.usecase.ValidateWord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class GameViewModel(
    wordLength: Int = 5,
    maxTurnCount: Int = 6,
    keyLayout: KeyLayout = QwertyLayout(),
) : CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private val _grid = MutableStateFlow(GridViewModel(wordLength, maxTurnCount))
    val grid: StateFlow<GridViewModel> = _grid.asStateFlow()

    private val _keyboard = MutableStateFlow(KeyboardViewModel(keyLayout))
    val keyboard: StateFlow<KeyboardViewModel> = _keyboard.asStateFlow()

    private val validate = ValidateWord(wordLength)

    init {
        launch {
            keyboard.collectLatest { listenToEvents(it) }
        }
    }

    private fun onLetterTapped(letter: Letter) {
        when (letter.char) {
            Letter.deleteChar -> grid.value.deleteLastLetter()
            Letter.enterChar -> performValidation()
            else -> grid.value.addLetterToGrid(letter)
        }
        grid.value.addLetterToGrid(letter)
    }

    private fun performValidation() = launch {
        validate(grid.value.lastWord)
    }

    private suspend fun listenToEvents(keyboardViewModel: KeyboardViewModel) {
        keyboardViewModel.events.collect { event ->
            when (event) {
                is KeyTapped -> onLetterTapped(event.letter)
            }
        }
    }
}