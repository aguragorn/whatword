package com.aguragorn.whatword.web.keyboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.aguragorn.whatword.core.keyboard.model.Letter
import com.aguragorn.whatword.keyboard.ui.KeyboardViewModel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.keywords.auto
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text

@Composable
fun Keyboard(keyboardViewModel: KeyboardViewModel) {
    val keys = keyboardViewModel.keys.collectAsState(Dispatchers.Main)

    Div(attrs = {
        style {
            alignContent(AlignContent.Center)
        }
    }) {
        for ((rowIndex, row) in keys.value.letters.withIndex()) {
            Div(
                attrs = {
                    style {
                        display(DisplayStyle.Flex)
                        alignContent(AlignContent.Center)
                        alignItems(AlignItems.Center)
                        justifyContent(JustifyContent.Center)
                        if (rowIndex > 0) marginTop(8.px)
                    }
                }
            ) {
                for ((letterIndex, letter) in row.withIndex()) {
                    Key(
                        letter = letter,
                        keyboardViewModel = keyboardViewModel,
                        style = {
                            if (letterIndex > 0) marginLeft(8.px)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Key(
    letter: Letter,
    keyboardViewModel: KeyboardViewModel,
    style: StyleBuilder.() -> Unit = {},
) {
    Div(attrs = {
        style {
            width(if (letter.isControlChar) 55.px else 40.px)
            height(55.px)
            borderRadius(4.px)
            backgroundColor(rgba(0, 0, 0, 0.15))
            textAlign("center")
            display(DisplayStyle.Flex)
            alignContent(AlignContent.Center)
            alignItems(AlignItems.Center)
            justifyContent(JustifyContent.Center)
            lineHeight(55.px)
            fontFamily("sans-serif")
            fontSize(1.6.em)

            // apply style-overrides set by caller
            style()
        }
        onClick { keyboardViewModel.onKeyTapped(letter) }
    }) {
        when {
            letter.isControlChar -> ControlChar(letter)
            else -> Text(letter.char.toString())
        }
    }
}

@Composable
fun ControlChar(letter: Letter) {
    val iconUrl = when (letter.char) {
        Letter.enterChar -> "https://css.gg/arrow-long-right.svg"
        else -> "https://css.gg/backspace.svg"
    }

    Img(src = iconUrl,
        attrs = {
            style {
                width(40.px)
                height(auto)
            }
        }
    )
}