package com.aguragorn.whatword.keyboard.ui

import com.aguragorn.whatword.core.keyboard.model.Letter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class KeyboardViewModel {
    private val _keys = MutableStateFlow(listOf<Letter>())
    val keys: StateFlow<List<Letter>> = _keys.asStateFlow()

    fun updateKeys(letters: List<Letter>) {
        val statuses = letters.associateBy { it.char }

        for (key in _keys.value) {
            statuses[key.char]?.let { key.status = it.status }
        }

        // TODO: Remember keys state

        // force subscribers of [keys] to be updated
        _keys.value = _keys.value.toList()
    }
}