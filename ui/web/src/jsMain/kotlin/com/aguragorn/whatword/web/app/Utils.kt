package com.aguragorn.whatword.web.app

import kotlinx.browser.window
import org.jetbrains.compose.web.css.CSSNumericValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.px

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

fun gameWidth(): CSSNumericValue<out CSSUnit> {
    return if (screenSize() == ScreenSize.SMALL) matchParent else 480.px
}