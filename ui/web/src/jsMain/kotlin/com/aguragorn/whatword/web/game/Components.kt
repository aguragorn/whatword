package com.aguragorn.whatword.web.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.aguragorn.whatword.game.ui.GameViewModel
import com.aguragorn.whatword.web.app.HStack
import com.aguragorn.whatword.web.app.Spacer
import com.aguragorn.whatword.web.app.VStack
import com.aguragorn.whatword.web.app.utils.matchParent
import com.aguragorn.whatword.web.grid.Grid
import com.aguragorn.whatword.web.keyboard.Keyboard
import com.aguragorn.whatword.web.theme.appTheme
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.maxWidth
import org.jetbrains.compose.web.css.width

@Composable
fun GameScreen(
    gameViewModel: GameViewModel
) {
    val theme by appTheme.collectAsState()
    HStack(attrs = {
        style {
            width(matchParent)
            height(matchParent)
            backgroundColor(theme.backgroundColor)
        }
    }) {
        Spacer()
        GameSurface(gameViewModel)
        Spacer()
    }
}

@Composable
private fun GameSurface(
    gameViewModel: GameViewModel
) {
    val keyboard by gameViewModel.keyboard.collectAsState()
    val grid by gameViewModel.grid.collectAsState()
    val gameWidth by currentGameWidth().collectAsState(gameWidth())

    VStack(attrs = {
        id("game-component")
        style {
            maxWidth(gameWidth)
            height(matchParent)
        }
    }) {

        Spacer()

        HStack(attrs = {
            style { width(matchParent) }
        }) { Spacer(); Grid(grid); Spacer(); }

        Spacer()

        Keyboard(keyboard)
    }
}