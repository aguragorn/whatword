package com.aguragorn.whatword.web.theme.colors

import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.Color

open class Colors(
    val background: CSSColorValue = Color.white,
    val backgroundInverse: CSSColorValue = Color("#212121"),
    val foreground: CSSColorValue = Color("#212121"),
    val foregroundInverse: CSSColorValue = Color.white,

    val surface: CSSColorValue = Color("#E0E0E0"),
    val onSurface: CSSColorValue = Color.black,

    val primary: CSSColorValue = Color("#00B0FF"),
    val onPrimary: CSSColorValue = Color.black,

    val secondary: CSSColorValue = Color("#FF6D00"),
    val onSecondary: CSSColorValue = Color.black,

    val negative: CSSColorValue = Color("#757575"),
    val onNegative: CSSColorValue = Color.white,
)