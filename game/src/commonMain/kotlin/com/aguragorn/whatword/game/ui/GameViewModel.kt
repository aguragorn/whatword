package com.aguragorn.whatword.game.ui

import com.aguragorn.whatword.di.gameDI
import com.aguragorn.whatword.grid.ui.GridViewModel
import com.aguragorn.whatword.keyboard.model.Event.KeyTapped
import com.aguragorn.whatword.keyboard.model.KeyLayout
import com.aguragorn.whatword.keyboard.model.Letter
import com.aguragorn.whatword.keyboard.model.QwertyLayout
import com.aguragorn.whatword.keyboard.ui.KeyboardViewModel
import com.aguragorn.whatword.statistics.di.statsDi
import com.aguragorn.whatword.statistics.usecase.SaveGamesStats
import com.aguragorn.whatword.validator.model.IncorrectLengthException
import com.aguragorn.whatword.validator.usecase.ValidateWord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.kodein.di.instance
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration

class GameViewModel(
    private val language: String = "english",
    private val wordLength: Int = 5,
    private val maxTurnCount: Int = 6,
    keyLayout: KeyLayout = QwertyLayout(),
    private val validate: ValidateWord = gameDI.instance(arg = wordLength),
    private val saveGameStats: SaveGamesStats = statsDi.instance()
) : CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private val _grid = MutableStateFlow(GridViewModel(wordLength, maxTurnCount))
    val grid: StateFlow<GridViewModel> = _grid.asStateFlow()

    private val _keyboard = MutableStateFlow(KeyboardViewModel(keyLayout))
    val keyboard: StateFlow<KeyboardViewModel> = _keyboard.asStateFlow()


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
    }

    private fun performValidation() = launch {
        try {
            val validatedWord = validate(grid.value.lastWord)

            keyboard.value.updateKeys(validatedWord.letters)
            grid.value.onWordValidated(validatedWord)

            val isWon = validatedWord.letters.none { it.status != Letter.Status.CORRECT }

            if (isWon || grid.value.words.value.size == maxTurnCount) {
                saveGameStats(
                    language = language,
                    wordLength = wordLength,
                    isWon = isWon,
                    time = Duration.ZERO,
                    rounds = grid.value.words.value.size
                )
            } else {
                grid.value.newWord()
            }

            // TODO: Show stats
            // TODO: Share game stats

        } catch (e: IncorrectLengthException) {
            // TODO: Show error
        }
    }

    private suspend fun listenToEvents(keyboardViewModel: KeyboardViewModel) {
        keyboardViewModel.events.collect { event ->
            when (event) {
                is KeyTapped -> onLetterTapped(event.letter)
            }
        }
    }
}