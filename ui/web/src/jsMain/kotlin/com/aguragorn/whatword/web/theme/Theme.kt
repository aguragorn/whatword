package com.aguragorn.whatword.web.theme

import com.aguragorn.whatword.core.keyboard.model.Letter
import com.aguragorn.whatword.web.theme.colors.Colors
import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.compose.web.css.CSSColorValue

val appTheme = MutableStateFlow(Theme())

open class Theme {
    val colors: Colors = Colors()

    val backgroundColor: CSSColorValue = colors.background
    val foregroundColor: CSSColorValue = colors.foreground

    fun keyColorFor(status: Letter.Status): CSSColorValue = when (status) {
        Letter.Status.UNKNOWN -> colors.surface
        Letter.Status.INCORRECT -> colors.negative
        Letter.Status.CORRECT -> colors.primary
        Letter.Status.MISPLACED -> colors.secondary
    }

    fun keyForegroundColorFor(status: Letter.Status): CSSColorValue = when (status) {
        Letter.Status.UNKNOWN -> colors.onSurface
        Letter.Status.INCORRECT -> colors.onNegative
        Letter.Status.CORRECT -> colors.onPrimary
        Letter.Status.MISPLACED -> colors.onSecondary
    }

    fun cellColorFor(status: Letter.Status): CSSColorValue = when (status) {
        Letter.Status.UNKNOWN -> colors.background
        Letter.Status.INCORRECT -> colors.negative
        Letter.Status.CORRECT -> colors.primary
        Letter.Status.MISPLACED -> colors.secondary
    }

    fun cellForegroundColorFor(status: Letter.Status): CSSColorValue = when (status) {
        Letter.Status.UNKNOWN -> colors.foreground
        Letter.Status.INCORRECT -> colors.onNegative
        Letter.Status.CORRECT -> colors.onPrimary
        Letter.Status.MISPLACED -> colors.onSecondary
    }
}