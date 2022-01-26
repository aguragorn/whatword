package com.aguragorn.whatword.web.keyboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.aguragorn.whatword.core.keyboard.model.Letter
import com.aguragorn.whatword.keyboard.ui.KeyboardViewModel
import com.aguragorn.whatword.web.theme.appTheme
import kotlinx.coroutines.Dispatchers
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.keywords.auto
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text

@Composable
fun Keyboard(keyboardViewModel: KeyboardViewModel) {
    val keys by keyboardViewModel.keys.collectAsState(Dispatchers.Main)

    Div(attrs = {
        id("keyboard")
        style {
            display(DisplayStyle.InlineBlock)
            alignContent(AlignContent.Center)
            marginBottom(8.px)
        }
    }) {
        for (row in keys.letters) {
            Div(attrs = {
                style {
                    display(DisplayStyle.Flex)
                    alignContent(AlignContent.Center)
                    alignItems(AlignItems.Center)
                    justifyContent(JustifyContent.Center)
                    marginBottom(8.px)
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
    val theme by appTheme.collectAsState()
    val keyHeight = 56.px
    val keyWidth = if (letter.isControlChar) 56.px else 40.px

    Button(attrs = {
        id("key-${letter.char}")
        style {
            width(keyWidth)
            height(keyHeight)
            borderRadius(4.px)
            color(theme.keyForegroundColorFor(letter.status))
            backgroundColor(theme.keyColorFor(letter.status))
            textAlign("center")
            display(DisplayStyle.Flex)
            alignContent(AlignContent.Center)
            alignItems(AlignItems.Center)
            justifyContent(JustifyContent.Center)
            lineHeight(keyHeight)
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
        })
}