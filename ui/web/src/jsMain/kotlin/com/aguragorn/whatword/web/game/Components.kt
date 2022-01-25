package com.aguragorn.whatword.web.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.aguragorn.whatword.game.ui.GameViewModel
import com.aguragorn.whatword.web.app.ScreenSize
import com.aguragorn.whatword.web.app.Spacer
import com.aguragorn.whatword.web.app.screenSize
import com.aguragorn.whatword.web.keyboard.Keyboard
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div

@Composable
fun Game(gameViewModel: GameViewModel = GameViewModel()) {
    val keyboard = gameViewModel.keyboard.collectAsState()
    val gameWidth = if (screenSize() == ScreenSize.SMALL) 100.vw else 480.px

    Div(
        attrs = {
            id("game")
            style {
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
                maxWidth(gameWidth)
                height(100.percent)
            }
        }
    ) {
        Spacer()
        Keyboard(keyboard.value)
    }
}