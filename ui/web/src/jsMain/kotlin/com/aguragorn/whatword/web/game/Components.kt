package com.aguragorn.whatword.web.game

import androidx.compose.runtime.*
import com.aguragorn.whatword.game.ui.GameViewModel
import com.aguragorn.whatword.web.app.ScreenSize
import com.aguragorn.whatword.web.app.Spacer
import com.aguragorn.whatword.web.app.screenSize
import com.aguragorn.whatword.web.grid.Grid
import com.aguragorn.whatword.web.keyboard.Keyboard
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div

@Composable
fun Game(
    gameViewModel: GameViewModel = GameViewModel(),
    onShowStatsChanged: (shouldShow: Boolean) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val keyboard by gameViewModel.keyboard.collectAsState()
    val grid by gameViewModel.grid.collectAsState()

    val gameWidth = if (screenSize() == ScreenSize.SMALL) 100.vw else 480.px

    remember {
        scope.launch { gameViewModel.showStats.collect(onShowStatsChanged) }
    }

    Div(attrs = {
        id("game")
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            maxWidth(gameWidth)
            height(100.percent)
        }
    }) {
        Spacer()

        Grid(grid)

        Spacer()

        Keyboard(keyboard)
    }
}