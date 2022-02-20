package com.aguragorn.whatword.web.app.utils

import org.jetbrains.compose.web.css.*

val borderLessButton: StyleBuilder.() -> Unit
    get() = {
        border(style = LineStyle.None)
        backgroundColor(Color.transparent)
    }