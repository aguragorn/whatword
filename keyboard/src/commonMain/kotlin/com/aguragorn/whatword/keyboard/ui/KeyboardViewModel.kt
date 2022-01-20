package com.aguragorn.whatword.keyboard.ui

import com.aguragorn.whatword.keyboard.ui.model.Key
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class KeyboardViewModel {
    private val _keys = MutableStateFlow(listOf<Key>())
    val keys: StateFlow<List<Key>> = _keys.asStateFlow()

    fun updateKeys(
        correctLetters: List<Char> = listOf(),
        misplacedLetters: List<Char> = listOf(),
        incorrectLetters: List<Char> = listOf(),
    ) {
        for (key in _keys.value) {
            when (key.char) {
                in correctLetters -> key.status = Key.Status.CORRECT
                in misplacedLetters -> key.status = Key.Status.MISPLACED
                in incorrectLetters -> key.status = Key.Status.INCORRECT
            }
        }

        // TODO: Remember keys state

        _keys.value = _keys.value.toList()
    }
}