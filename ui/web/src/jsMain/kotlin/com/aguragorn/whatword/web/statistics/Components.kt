package com.aguragorn.whatword.web.statistics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.aguragorn.whatword.statistics.model.Stats
import com.aguragorn.whatword.statistics.model.roundWithMostWins
import com.aguragorn.whatword.statistics.ui.StatisticsViewModel
import com.aguragorn.whatword.web.app.HStack
import com.aguragorn.whatword.web.app.Spacer
import com.aguragorn.whatword.web.app.Title
import com.aguragorn.whatword.web.app.VStack
import com.aguragorn.whatword.web.app.utils.borderLessButton
import com.aguragorn.whatword.web.app.utils.matchParent
import com.aguragorn.whatword.web.app.utils.wrapContent
import com.aguragorn.whatword.web.game.currentGameWidth
import com.aguragorn.whatword.web.game.gameWidth
import com.aguragorn.whatword.web.theme.appTheme
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import kotlin.math.roundToInt

@Composable
fun Stats(statsViewModel: StatisticsViewModel) {
    val theme by appTheme.collectAsState()
    val showStats = statsViewModel.showStats.collectAsState(false)

    if (!showStats.value) return

    HStack(attrs = {
        id("stats-component")
        style {
            width(matchParent)
            height(matchParent)
            position(Position.Absolute)
            margin(0.px)
            backgroundColor(theme.backgroundColor)
        }
    }) {
        Spacer()
        StatsSurface(statsViewModel)
        Spacer()
    }
}

@Composable
private fun StatsSurface(
    statsViewModel: StatisticsViewModel
) {
    val stats by statsViewModel.stats.collectAsState()
    val gameWidth by currentGameWidth().collectAsState(gameWidth())

    VStack(attrs = {
        id("stats-surface")
        style {
            width(gameWidth)
            height(matchParent)
        }
    }) {
        StatsHeader(statsViewModel)
        stats?.let { MainStats(stats = it) }
        stats?.let { RoundsStats(stats = it) }
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
        HStack(attrs = {
            style { width(matchParent) }
        }) {
            Button(attrs = {
                style {
                    borderLessButton()
                    width(matchParent)
                    flexGrow(1)
                }
                onClick { statsViewModel.hideStats() }
            }) {
                HStack(attrs = {
                    style { alignItems(AlignItems.Center) }
                }) {
                    Img(
                        src = "icons/ic_stats_close.svg",
                        alt = "close stats"
                    )
                    Text("Close")
                }
            }

            Title(text = "Statistics",
                attrs = {
                    style {
                        width(matchParent)
                        flexGrow(1)
                    }
                })

            Spacer(attrs = { style { width(matchParent) } })
        }
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
        Stat("${stats.winRate.roundToInt()}", "Win %")
        Stat("${stats.currentStreak}", "Current\r\nStreak")
        Stat("${stats.bestStreak}", "Best\r\nStreak")

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