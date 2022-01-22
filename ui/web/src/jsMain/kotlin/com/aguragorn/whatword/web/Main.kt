package com.aguragorn.whatword.web

import com.aguragorn.whatword.core.keyboard.model.Letter
import com.aguragorn.whatword.keyboard.ui.KeyboardViewModel
import com.aguragorn.whatword.web.keyboard.Key
import org.jetbrains.compose.web.renderComposable

fun main() {
    val keyboard = KeyboardViewModel()

    renderComposable(rootElementId = "root") {
        Key(Letter('A'), keyboard)
    }
}