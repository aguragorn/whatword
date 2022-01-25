package com.aguragorn.whatword.web.grid

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.aguragorn.whatword.core.keyboard.model.Letter
import com.aguragorn.whatword.grid.ui.GridViewModel
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@Composable
fun Grid(
    gridViewModel: GridViewModel
) {
    val words by gridViewModel.words.collectAsState()

    for (wordIndex in 0 until gridViewModel.maxTurnCount) {
        val word = words.elementAtOrNull(wordIndex)
        Div(attrs = {
            style {
                display(DisplayStyle.Flex)
                alignContent(AlignContent.Center)
                alignItems(AlignItems.Center)
                justifyContent(JustifyContent.Center)
                marginBottom(4.px)
            }
        }
        ) {
            for (letterIndex in 0 until gridViewModel.wordLength) {
                Cell(letter = word?.letters?.elementAtOrNull(letterIndex),
                    style = { if (letterIndex > 0) marginLeft(4.px) })
            }
        }
    }
}

@Composable
fun Cell(
    letter: Letter?,
    style: StyleBuilder.() -> Unit = {},
) {
    Div(attrs = {
        style {
            width(48.px)
            height(48.px)
            border { width = 2.px; this.style = LineStyle.Solid; color = Color("#808080"); }
            textAlign("center")
            display(DisplayStyle.Flex)
            alignContent(AlignContent.Center)
            alignItems(AlignItems.Center)
            justifyContent(JustifyContent.Center)
            lineHeight(48.px)
            fontFamily("sans-serif")
            fontSize(1.6.em)

            // apply style-overrides set by caller
            style()
        }
    }) {
        Text(letter?.char?.uppercase().orEmpty())
    }
}