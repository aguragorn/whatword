package com.aguragorn.whatword.web

import com.aguragorn.whatword.di.gameDI
import com.aguragorn.whatword.game.ui.GameViewModel
import com.aguragorn.whatword.toaster.ToasterViewModel
import com.aguragorn.whatword.web.app.ScreenSize
import com.aguragorn.whatword.web.app.Spacer
import com.aguragorn.whatword.web.app.screenSize
import com.aguragorn.whatword.web.game.Game
import com.aguragorn.whatword.web.toaster.Toaster
import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.renderComposable
import org.kodein.di.instance

@OptIn(ExperimentalComposeWebApi::class)
fun main() {
    val toasterViewModel = gameDI.instance<ToasterViewModel>()
    val gameViewModel = gameDI.instance<GameViewModel>()

    renderComposable(rootElementId = "root") {

        if (screenSize() != ScreenSize.SMALL) Spacer()
        Toaster(toasterViewModel)
        Game(gameViewModel, onShowStatsChanged = { println(it) })
        if (screenSize() != ScreenSize.SMALL) Spacer()
    }
}