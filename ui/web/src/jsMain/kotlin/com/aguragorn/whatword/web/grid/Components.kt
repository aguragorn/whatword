@file:Suppress("DuplicatedCode")

package com.aguragorn.whatword.web.grid

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.aguragorn.whatword.grid.ui.GridViewModel
import com.aguragorn.whatword.keyboard.model.Letter
import com.aguragorn.whatword.web.theme.appTheme
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@Composable
fun Grid(
    gridViewModel: GridViewModel
) {
    val words by gridViewModel.words.collectAsState()

    val gridSpacing = 8.px

    for (wordIndex in 0 until gridViewModel.maxTurnCount) {
        val word = words.elementAtOrNull(wordIndex)

        Div(attrs = {
            style {
                display(DisplayStyle.Flex)
                alignContent(AlignContent.Center)
                alignItems(AlignItems.Center)
                justifyContent(JustifyContent.Center)
                marginBottom(gridSpacing)
            }
        }) {
            for (letterIndex in 0 until gridViewModel.wordLength) {
                Cell(letter = word?.letters?.elementAtOrNull(letterIndex),
                    style = { if (letterIndex > 0) marginLeft(gridSpacing) })
            }
        }
    }
}

@Composable
fun Cell(
    letter: Letter?,
    style: StyleBuilder.() -> Unit = {},
) {
    val theme by appTheme.collectAsState()

    val cellSize = 52.px

    Div(attrs = {
        style {
            width(cellSize)
            height(cellSize)
            letter?.let {
                color(theme.cellForegroundColorFor(it.status))
                backgroundColor(theme.cellColorFor(it.status))
            }
            border { width = 2.px; this.style = LineStyle.Solid; color = Color("#808080"); }
            textAlign("center")
            display(DisplayStyle.Flex)
            alignContent(AlignContent.Center)
            alignItems(AlignItems.Center)
            justifyContent(JustifyContent.Center)
            lineHeight(cellSize)
            fontFamily("sans-serif")
            fontSize(1.6.em)

            // apply style-overrides set by caller
            style()
        }
    }) {
        Text(letter?.char?.uppercase().orEmpty())
    }
}