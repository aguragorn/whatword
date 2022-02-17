package com.aguragorn.whatword.web.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.aguragorn.whatword.game.ui.GameViewModel
import com.aguragorn.whatword.web.app.*
import com.aguragorn.whatword.web.grid.Grid
import com.aguragorn.whatword.web.keyboard.Keyboard
import org.jetbrains.compose.web.css.fontWeight
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.maxWidth
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.H4
import org.jetbrains.compose.web.dom.Text

@Composable
fun Game(
    gameViewModel: GameViewModel
) {
    val keyboard by gameViewModel.keyboard.collectAsState()
    val grid by gameViewModel.grid.collectAsState()
    val mysteryWord = gameViewModel.mysteryWord.collectAsState()

    VStack(attrs = {
        id("game-component")
        style {
            maxWidth(gameWidth())
            height(matchParent)
        }
    }) {

        mysteryWord.value?.puzzleNumber?.let { puzzleNo ->
            HStack(attrs = {
                style { width(matchParent) }
            }) {
                Spacer()
                H4(attrs = {
                    style { fontWeight("bold") }
                }) { Text("Mystery Word #$puzzleNo") }
                Spacer()
            }
        }

        Spacer()

        HStack(attrs = {
            style { width(matchParent) }
        }) { Spacer(); Grid(grid); Spacer(); }

        Spacer()

        Keyboard(keyboard)
    }
}