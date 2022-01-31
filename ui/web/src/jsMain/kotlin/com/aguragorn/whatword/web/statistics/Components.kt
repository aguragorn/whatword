package com.aguragorn.whatword.web.statistics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.aguragorn.whatword.statistics.ui.StatisticsViewModel
import com.aguragorn.whatword.web.app.HStack
import com.aguragorn.whatword.web.app.ScreenSize
import com.aguragorn.whatword.web.app.Spacer
import com.aguragorn.whatword.web.app.screenSize
import com.aguragorn.whatword.web.theme.appTheme
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div

@Composable
fun Stats(statsViewModel: StatisticsViewModel) {
    val theme = appTheme.collectAsState()
    val gameWidth = if (screenSize() == ScreenSize.SMALL) 100.vw else 480.px

    val showStats = statsViewModel.showStats.collectAsState(false)
    if (!showStats.value) return

    HStack(attrs = {
        id("stats")
        style {
            width(100.percent)
            height(100.percent)
            position(Position.Absolute)
            margin(0.px)
        }
    }) {
        Spacer()
        Div(attrs = {
            style {
                backgroundColor(theme.value.backgroundColor)
                width(gameWidth)
                height(100.percent)
            }
        }) {
            // TODO: Close button
            // TODO: Display actual stats
        }
        Spacer()
    }
}