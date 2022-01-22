package com.aguragorn.whatword.keyboard.ui.com.aguragorn.whatword.keyboard.model

import com.aguragorn.whatword.core.keyboard.model.Letter

sealed class Event {
    class KeyTapped(val letter: Letter) : Event()
}