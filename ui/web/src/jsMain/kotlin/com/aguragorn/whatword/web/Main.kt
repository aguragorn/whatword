package com.aguragorn.whatword.web

import androidx.compose.runtime.collectAsState
import com.aguragorn.whatword.game.ui.GameViewModel
import com.aguragorn.whatword.statistics.ui.StatisticsViewModel
import com.aguragorn.whatword.toaster.ToasterViewModel
import com.aguragorn.whatword.web.app.VStack
import com.aguragorn.whatword.web.app.utils.makeResponsive
import com.aguragorn.whatword.web.app.utils.matchParent
import com.aguragorn.whatword.web.di.DI
import com.aguragorn.whatword.web.game.GameScreen
import com.aguragorn.whatword.web.menu.Menu
import com.aguragorn.whatword.web.statistics.Stats
import com.aguragorn.whatword.web.theme.appTheme
import com.aguragorn.whatword.web.toaster.Toaster
import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.flexGrow
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.renderComposable
import org.kodein.di.instance

@OptIn(ExperimentalComposeWebApi::class)
fun main() {
    val toasterViewModel = DI.instance<ToasterViewModel>()
    val statisticsViewModel = DI.instance<StatisticsViewModel>()
    val gameViewModel = DI.instance<GameViewModel>()

    makeResponsive()

    renderComposable(rootElementId = "root") {
        val theme = appTheme.collectAsState().value

        val puzzleNo = gameViewModel.mysteryWord.collectAsState().value?.puzzleNumber ?: 0


        VStack(attrs = {
            style {
                width(matchParent)
                height(matchParent)
                flexGrow(1)
                backgroundColor(theme.backgroundColor)
            }
        }) {
            Menu(
                statsViewModel = statisticsViewModel,
                puzzleNo = puzzleNo
            )
            // TODO: Settings Screen
            GameScreen(gameViewModel)
            Toaster(toasterViewModel)
            Stats(statisticsViewModel)

        }
    }
}