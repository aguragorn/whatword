package com.aguragorn.whatword.keyboard.model

sealed class Event {
    class KeyTapped(val letter: Letter) : Event()
}