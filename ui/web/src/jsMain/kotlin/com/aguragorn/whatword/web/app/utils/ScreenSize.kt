package com.aguragorn.whatword.web.app.utils

import kotlinx.browser.window
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.w3c.dom.events.Event

enum class ScreenSize {
    SMALL, MEDIUM, LARGE, XLARGE
}

private val screenSizeSubject = MutableStateFlow(getScreenSize())
val currentScreenSize: StateFlow<ScreenSize> = screenSizeSubject.asStateFlow()
private val screenSizeUpdater: (Event) -> Unit = { screenSizeSubject.update { getScreenSize() } }

fun makeResponsive() {
    window.removeEventListener("resize", screenSizeUpdater, true)
    window.addEventListener("resize", screenSizeUpdater, true)
}

fun getScreenSize(): ScreenSize {
    val width = window.innerWidth
    return when {
        width <= 480 -> ScreenSize.SMALL
        width <= 840 -> ScreenSize.MEDIUM
        width <= 1280 -> ScreenSize.LARGE
        else -> ScreenSize.XLARGE
    }
}