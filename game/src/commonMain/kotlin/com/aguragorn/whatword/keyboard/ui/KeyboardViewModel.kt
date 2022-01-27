package com.aguragorn.whatword.keyboard.ui

import com.aguragorn.whatword.keyboard.model.Event
import com.aguragorn.whatword.keyboard.model.KeyLayout
import com.aguragorn.whatword.keyboard.model.Letter
import com.aguragorn.whatword.keyboard.model.Letter.Status
import com.aguragorn.whatword.keyboard.model.QwertyLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class KeyboardViewModel(
    keyLayout: KeyLayout = QwertyLayout()
) : CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Default

    private val _keys = MutableStateFlow(keyLayout)
    val keys: StateFlow<KeyLayout> = _keys.asStateFlow()

    private val _events = MutableSharedFlow<Event>(extraBufferCapacity = UNLIMITED)
    val events: SharedFlow<Event> = _events.asSharedFlow()

    fun updateKeys(letters: List<Letter>) {
        val statuses = letters
            .groupBy { it.char }
            .mapValues { (_, value) -> value.map { it.status } }
        val validatedKeys = _keys.value.letters

        for (validatedKey in validatedKeys.flatten()) {
            val keyChar = validatedKey.char
            val newStatus = statuses[keyChar]?.maxByOrNull { it.weight } ?: Status.UNKNOWN

            validatedKey.status = if (validatedKey.status.weight < newStatus.weight) newStatus else continue
        }

        // TODO: Remember keys state

        // force subscribers of [keys] to be updated
        _keys.value = KeyLayout(validatedKeys)
    }

    fun onKeyTapped(letter: Letter) = launch(coroutineContext) {
        _events.tryEmit(Event.KeyTapped(letter.copy().apply { status = Status.UNKNOWN }))
    }
}