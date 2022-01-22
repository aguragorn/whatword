package com.aguragorn.whatword.web.keyboard

import androidx.compose.runtime.Composable
import com.aguragorn.whatword.core.keyboard.model.Letter
import com.aguragorn.whatword.keyboard.ui.KeyboardViewModel
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@Composable
fun Key(letter: Letter, keyboardViewModel: KeyboardViewModel) {
    Div(attrs = {
        style {
            width(40.px)
            height(55.px)
            borderRadius(4.px)
            backgroundColor(rgba(0, 0, 0, 0.25))
            textAlign("center")
            lineHeight(58.px)
            fontFamily("sans-serif")
            fontSize(1.75.em)
        }
        onClick { keyboardViewModel.onKeyTapped(letter) }
    }) {
        Text(letter.char.toString())
    }
}