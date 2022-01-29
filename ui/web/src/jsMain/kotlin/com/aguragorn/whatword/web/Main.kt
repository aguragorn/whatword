package com.aguragorn.whatword.web

import com.aguragorn.whatword.game.ui.GameViewModel
import com.aguragorn.whatword.web.app.ScreenSize
import com.aguragorn.whatword.web.app.Spacer
import com.aguragorn.whatword.web.app.screenSize
import com.aguragorn.whatword.web.game.Game
import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.renderComposable

@OptIn(ExperimentalComposeWebApi::class)
fun main() {
    val gameViewModel = GameViewModel()
    renderComposable(rootElementId = "root") {

        if (screenSize() != ScreenSize.SMALL) Spacer()
        Game(gameViewModel, onShowStatsChanged = { println(it) })
        if (screenSize() != ScreenSize.SMALL) Spacer()
    }
}