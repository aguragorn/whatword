package com.aguragorn.whatword.statistics.ui

import com.aguragorn.whatword.statistics.model.Stats
import com.aguragorn.whatword.statistics.usecase.GetGameStats
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StatisticsViewModel(
    private val getGameStats: GetGameStats
) : CoroutineScope {
    override val coroutineContext = Dispatchers.Main

    private val _stats = MutableStateFlow<Stats?>(null)
    val stats: StateFlow<Stats?> = _stats.asStateFlow()
    val showStats: Flow<Boolean> = _stats.map { it != null }
    val showLastWord: Flow<Boolean> = _stats.map { it?.isLastRoundWon == false }

    fun showGamesStats(language: String, wordLength: Int) = launch {
        _stats.value = getGameStats(language = language, wordLength = wordLength)
    }

    fun hideStats() {
        _stats.value = null
    }
}