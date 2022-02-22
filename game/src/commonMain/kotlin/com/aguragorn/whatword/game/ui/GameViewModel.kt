package com.aguragorn.whatword.game.ui

import com.aguragorn.whatword.config.model.GameConfig
import com.aguragorn.whatword.game.model.MysteryWord
import com.aguragorn.whatword.game.usecase.RandomMysteryWord
import com.aguragorn.whatword.grid.ui.GridViewModel
import com.aguragorn.whatword.keyboard.model.Event.KeyTapped
import com.aguragorn.whatword.keyboard.model.KeyLayout
import com.aguragorn.whatword.keyboard.model.Letter
import com.aguragorn.whatword.keyboard.model.QwertyLayout
import com.aguragorn.whatword.keyboard.ui.KeyboardViewModel
import com.aguragorn.whatword.statistics.ui.StatisticsViewModel
import com.aguragorn.whatword.statistics.usecase.GetGameStats
import com.aguragorn.whatword.statistics.usecase.SaveGamesStats
import com.aguragorn.whatword.toaster.ToasterViewModel
import com.aguragorn.whatword.toaster.model.Message
import com.aguragorn.whatword.utils.today
import com.aguragorn.whatword.validator.usecase.ValidateWord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration

class GameViewModel constructor(
    private val config: GameConfig = GameConfig.default,
    private val getGameStats: GetGameStats,
    private val isPractice: Boolean = false,
    keyLayout: KeyLayout = QwertyLayout(),
    private val randomMysteryWord: RandomMysteryWord,
    private val saveGameStats: SaveGamesStats,
    private val statsViewModel: StatisticsViewModel,
    private val toaster: ToasterViewModel,
    private val validate: ValidateWord,
) : CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private val _grid = MutableStateFlow(GridViewModel(config.wordLength, config.maxTurnCount))
    val grid: StateFlow<GridViewModel> = _grid.asStateFlow()

    private val _keyboard = MutableStateFlow(KeyboardViewModel(keyLayout))
    val keyboard: StateFlow<KeyboardViewModel> = _keyboard.asStateFlow()

    private val _mysteryWord = MutableStateFlow<MysteryWord?>(null)
    val mysteryWord: StateFlow<MysteryWord?> = _mysteryWord.asStateFlow()

    init {
        launch {
            val today = if (!isPractice) LocalDate.today() else null

            _mysteryWord.value = randomMysteryWord.invoke(
                config = config,
                date = today
            )

            // TODO: load or create session


            keyboard.collectLatest { listenToEvents(it) }
        }
    }

    private fun onLetterTapped(letter: Letter) {
        when (letter.char) {
            Letter.deleteChar -> grid.value.deleteLastLetter()
            Letter.enterChar -> performValidation()
            else -> grid.value.addLetterToGrid(letter)
        }

        // TODO: update session
    }

    private suspend fun hasPlayed() = withContext(Dispatchers.Main) {
        getGameStats.invoke(config).lastMysteryWord == _mysteryWord.value?.word
    }

    private fun performValidation() = launch {
        if (hasPlayed()) return@launch
        val mysteryWord = _mysteryWord.value ?: return@launch

        try {
            val validatedWord = validate.invoke(
                attempt = grid.value.lastWord,
                mysteryWord = mysteryWord.word,
                config = config,
            )

            keyboard.value.updateKeys(validatedWord.letters)
            grid.value.onWordValidated(validatedWord)

            val isWon = validatedWord.letters.none { it.status != Letter.Status.CORRECT }

            if (isWon || grid.value.words.value.size == config.maxTurnCount) {
                saveGameStats.invoke(
                    config = config,
                    isWon = isWon,
                    time = Duration.ZERO,
                    rounds = grid.value.words.value.size,
                    mysteryWord = mysteryWord.word,
                )

                statsViewModel.showGamesStats(config = config)
            } else {
                grid.value.newWord()
            }
        } catch (e: Throwable) {
            toaster.show(
                Message(
                    type = Message.Type.ERROR,
                    text = e.message ?: "Something went wrong. Please try another word"
                )
            )
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