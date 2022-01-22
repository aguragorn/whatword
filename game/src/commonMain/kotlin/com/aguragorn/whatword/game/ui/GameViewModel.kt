package com.aguragorn.whatword.game.ui

import com.aguragorn.whatword.core.keyboard.model.Letter
import com.aguragorn.whatword.grid.ui.GridViewModel
import com.aguragorn.whatword.keyboard.model.Event.KeyTapped
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
    private val wordLength: Int = 5,
    private val maxTurnCount: Int = 6,
) : CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private val _grid = MutableStateFlow(GridViewModel(wordLength, maxTurnCount))
    val grid: StateFlow<GridViewModel> = _grid.asStateFlow()

    private val _keyboard = MutableStateFlow(KeyboardViewModel())
    val keyboard: StateFlow<KeyboardViewModel> = _keyboard.asStateFlow()

    private val validate = ValidateWord(wordLength)

    init {
        launch {
            keyboard.collectLatest { listenToEvents(it) }
        }
    }

    private fun onLetterTapped(letter: Letter) {
        when (letter.char) {
            '<', '>' -> grid.value.deleteLastLetter()
            '\n' -> performValidation()
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