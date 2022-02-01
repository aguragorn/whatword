package com.aguragorn.whatword.web.statistics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.aguragorn.whatword.statistics.ui.StatisticsViewModel
import com.aguragorn.whatword.web.app.*
import com.aguragorn.whatword.web.theme.appTheme
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H5
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text

@Composable
fun Stats(statsViewModel: StatisticsViewModel) {
    val theme = appTheme.collectAsState()
    val gameWidth = if (screenSize() == ScreenSize.SMALL) 100.percent else 480.px

    val showStats = statsViewModel.showStats.collectAsState(false)
    val stats = statsViewModel.stats.collectAsState()
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
            StatsHeader(statsViewModel)
            // TODO: Display actual stats
        }
        Spacer()
    }
}

@Composable
fun StatsHeader(statsViewModel: StatisticsViewModel) {
    HStack(attrs = {
        style {
            backgroundColor(Color.transparent)
            border(style = LineStyle.None)
            width(100.percent)
            height(fitContent)
            padding(16.px)
            alignContent(AlignContent.Center)
        }
    }) {
        Img(src = "icons/ic_stats_close.svg",
            alt = "close stats",
            attrs = { onClick { statsViewModel.hideStats() } })
        Spacer()
        H5(attrs = { style { margin(0.px) } }) { Text(("Statistics")) }
        Spacer()
    }
}