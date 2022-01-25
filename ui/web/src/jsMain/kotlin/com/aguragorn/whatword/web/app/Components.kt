package com.aguragorn.whatword.web.app

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.flexGrow
import org.jetbrains.compose.web.css.minHeight
import org.jetbrains.compose.web.css.minWidth
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

@Composable
fun Spacer() {
    Div(attrs = {
        style {
            flexGrow(1)
            minWidth(0.px)
            minHeight(0.px)
        }
    })
}