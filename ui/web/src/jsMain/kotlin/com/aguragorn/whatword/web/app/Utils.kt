package com.aguragorn.whatword.web.app

import kotlinx.browser.window

enum class ScreenSize {
    SMALL, MEDIUM, LARGE
}

fun screenSize(): ScreenSize {
    val width = window.screen.availWidth
    return when {
        width <= 400 -> ScreenSize.SMALL
        width <= 600 -> ScreenSize.MEDIUM
        else -> ScreenSize.LARGE
    }
}