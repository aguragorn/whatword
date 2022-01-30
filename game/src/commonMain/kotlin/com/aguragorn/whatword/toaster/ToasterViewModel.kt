package com.aguragorn.whatword.toaster

import com.aguragorn.whatword.toaster.model.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration

class ToasterViewModel : CoroutineScope {
    companion object {
        val autoDismissDelay = Duration.parse("5s")
    }

    override val coroutineContext = Dispatchers.Main

    private val _messages = MutableStateFlow<List<Message>>(listOf())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private fun mutableMessages() = _messages.value.map { it.copy() }.toMutableList()

    fun show(message: Message) {
        _messages.value = mutableMessages().apply { add(message) }
        launch {
            delay(autoDismissDelay)
            mutableMessages().also { if (it.removeFirstOrNull() != null) _messages.value = it }
        }
    }

}