package com.aguragorn.whatword.keyboard.ui

import com.aguragorn.whatword.core.keyboard.model.Letter
import com.aguragorn.whatword.core.keyboard.model.Letter.Status
import com.aguragorn.whatword.keyboard.ui.com.aguragorn.whatword.keyboard.model.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class KeyboardViewModel : CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Default

    private val _keys = MutableStateFlow(listOf<Letter>())
    val keys: StateFlow<List<Letter>> = _keys.asStateFlow()

    private val _events = MutableSharedFlow<Event>(extraBufferCapacity = UNLIMITED)
    val events: SharedFlow<Event> = _events.asSharedFlow()

    fun updateKeys(letters: List<Letter>) {
        val statuses = letters
            .groupBy { it.char }
            .mapValues { (_, value) -> value.map { it.status } }
        val validatedKeys = _keys.value.toList()

        for (key in validatedKeys) {
            val keyChar = key.char

            key.status = when {
                statuses[keyChar]?.contains(Status.CORRECT) == true -> Status.CORRECT
                statuses[keyChar]?.contains(Status.MISPLACED) == true -> Status.MISPLACED
                statuses[keyChar]?.contains(Status.INCORRECT) == true -> Status.INCORRECT
                else -> Status.UNKNOWN
            }
        }

        // TODO: Remember keys state

        // force subscribers of [keys] to be updated
        _keys.value = validatedKeys
    }

    fun onKeyTapped(letter: Letter) = launch(coroutineContext) {
        _events.tryEmit(Event.KeyTapped(letter))
    }
}