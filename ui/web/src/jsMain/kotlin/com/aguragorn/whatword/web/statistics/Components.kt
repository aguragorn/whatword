package com.aguragorn.whatword.web.statistics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.aguragorn.whatword.statistics.model.Stats
import com.aguragorn.whatword.statistics.model.roundWithMostWins
import com.aguragorn.whatword.statistics.ui.StatisticsViewModel
import com.aguragorn.whatword.web.app.*
import com.aguragorn.whatword.web.theme.Theme
import com.aguragorn.whatword.web.theme.appTheme
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Composable
fun Stats(statsViewModel: StatisticsViewModel) {
    val theme = appTheme.collectAsState()
    val showStats = statsViewModel.showStats.collectAsState(false)

    if (!showStats.value) return

    HStack(attrs = {
        id("stats-component")
        style {
            width(matchParent)
            height(matchParent)
            position(Position.Absolute)
            margin(0.px)
        }
    }) {
        Spacer()
        StatsSurface(theme, statsViewModel)
        Spacer()
    }
}

@Composable
private fun StatsSurface(
    theme: State<Theme>,
    statsViewModel: StatisticsViewModel
) {
    val stats = statsViewModel.stats.collectAsState()

    VStack(attrs = {
        id("stats-surface")
        style {
            width(gameWidth())
            height(matchParent)
            backgroundColor(theme.value.backgroundColor)
            color(theme.value.foregroundColor)
        }
    }) {
        StatsHeader(statsViewModel)
        stats.value?.let { MainStats(stats = it) }
        stats.value?.let { RoundsStats(stats = it) }
    }
}

@Composable
fun StatsHeader(statsViewModel: StatisticsViewModel) {
    HStack(attrs = {
        id("stats-header")
        style {
            backgroundColor(Color.transparent)
            border(style = LineStyle.None)
            width(matchParent)
            height(wrapContent)
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

@Composable
fun MainStats(stats: Stats) {
    HStack(attrs = {
        style {
            width(matchParent)
            height(wrapContent)
        }
    }) {
        Spacer()

        Stat("${stats.gamesPlayed}", "Played")
        Stat("${stats.winRate}", "Win %")
        Stat("${stats.currentStreak}", "Current\r\nStreak")
        Stat("${stats.maxStreak}", "Best\r\nStreak")

        Spacer()
    }
}

@Composable
fun Stat(value: String, label: String) {
    VStack(attrs = {
        style {
            alignItems(AlignItems.Center)
            justifyContent(JustifyContent.Center)
            margin(8.px)
        }
    }
    ) {
        H1(attrs = {
            id("games-count")
            style {
                margin(0.px)
                padding(0.px)
            }
        }) { Text(value) }
        Span(attrs = {
            style {
                whiteSpace("pre-wrap")
                textAlign("center")
            }
        }) { Text(label) }

    }
}

@Composable
fun RoundsStats(stats: Stats) {
    val theme = appTheme.collectAsState().value
    val maxWins = stats.roundsStats.roundWithMostWins()?.numberOfGames ?: 0
    val chartWidth = 300

    VStack(attrs = {
        id("rounds-stats")
        style {
            width(matchParent)
            height(wrapContent)
        }
    }) {
        HStack(attrs = { style { width(matchParent) } }) {
            Spacer(); B { Text("TURNS TO WIN") }; Spacer()
        }
        HStack(attrs = { style { width(matchParent) } }) {
            Spacer()

            VStack(attrs = {
                style { minWidth(chartWidth.px) }
            }) {
                val roundsStats = stats.roundsStats.associateBy { it.guessCount }

                for (roundsCount in 1..stats.gameConfig.maxTurnCount) {
                    val gamesWonAtRoundsCount = roundsStats[roundsCount]?.numberOfGames ?: 0
                    val barWidth = (gamesWonAtRoundsCount.toDouble() / maxWins.toDouble()) * chartWidth

                    HStack(attrs = {
                        style {
                            alignItems(AlignItems.Center)
                            justifyContent(JustifyContent.Center)
                        }
                    }) {
                        Span(attrs = {
                            style {
                                width(wrapContent)
                                height(wrapContent)
                                margin(4.px)
                            }
                        }) { Text("$roundsCount") }

                        val winsInRoundCount = roundsStats[roundsCount]?.numberOfGames ?: 0
                        if (winsInRoundCount > 0) {
                            HStack(attrs = {
                                style {
                                    width(barWidth.px)
                                    backgroundColor(theme.colors.backgroundInverse)
                                    color(theme.colors.foregroundInverse)
                                    padding(4.px)
                                }
                            }) {
                                Spacer()
                                Text("$winsInRoundCount")
                            }
                        }
                    }
                }
            }

            Spacer()
        }
    }
}