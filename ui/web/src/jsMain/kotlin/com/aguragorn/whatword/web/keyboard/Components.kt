@file:Suppress("DuplicatedCode")

package com.aguragorn.whatword.web.keyboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.aguragorn.whatword.keyboard.model.Letter
import com.aguragorn.whatword.keyboard.ui.KeyboardViewModel
import com.aguragorn.whatword.web.app.VStack
import com.aguragorn.whatword.web.theme.appTheme
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.keywords.auto
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.events.KeyboardEvent

@Composable
fun Keyboard(keyboardViewModel: KeyboardViewModel) {
    val keys by keyboardViewModel.keys.collectAsState()
    VStack(attrs = {
        id("keyboard")
        style {
            alignContent(AlignContent.Center)
            alignItems(AlignItems.Center)
            justifyContent(JustifyContent.Center)
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

    remember {
        window.addEventListener("keyup", {
            val key = (it as? KeyboardEvent)?.key.orEmpty()
            if (key.equals("${letter.char}", ignoreCase = true)
                || (letter.char == Letter.enterChar && key == "Enter")
                || (letter.char == Letter.deleteChar && key == "Backspace")
            ) {
                keyboardViewModel.onKeyTapped(letter)
            }
        })
    }

    Button(attrs = {
        id("key-${letter.char}")
        style {
            width(keyWidth)
            height(keyHeight)
            border(style = LineStyle.None)
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
            cursor("pointer")

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
        Letter.enterChar -> "icons/ic_keyboard_enter.svg"
        else -> "icons/ic_keyboard_backspace.svg"
    }

    Img(src = iconUrl,
        attrs = {
            style {
                width(40.px)
                height(auto)
            }
        })
}