package com.aguragorn.whatword.web

import com.aguragorn.whatword.keyboard.ui.KeyboardViewModel
import com.aguragorn.whatword.web.keyboard.Keyboard
import kotlinx.browser.window
import org.jetbrains.compose.web.renderComposable

fun main() {
    val keyboard = KeyboardViewModel()
    println(window.screen.availWidth)

    renderComposable(rootElementId = "root") {
        Keyboard(keyboard)
    }
}