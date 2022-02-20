package com.aguragorn.whatword.web.menu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.aguragorn.whatword.config.model.GameConfig
import com.aguragorn.whatword.statistics.ui.StatisticsViewModel
import com.aguragorn.whatword.web.app.HStack
import com.aguragorn.whatword.web.app.Spacer
import com.aguragorn.whatword.web.app.Title
import com.aguragorn.whatword.web.app.utils.ScreenSize
import com.aguragorn.whatword.web.app.utils.borderLessButton
import com.aguragorn.whatword.web.app.utils.currentScreenSize
import com.aguragorn.whatword.web.app.utils.matchParent
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.AttrBuilderContext
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text

@Composable
fun Menu(
    statsViewModel: StatisticsViewModel,
    puzzleNo: Int
) {
    HStack(attrs = {
        id("menu")
        style {
            width(matchParent)
            position(Position.Fixed)
            alignItems(AlignItems.Center)
        }
    }) {
        Spacer(attrs = { style { width(matchParent) } })

        Title(text = "Mystery Word #$puzzleNo",
            attrs = { style { flexGrow(1) } })

        Options(statsViewModel = statsViewModel,
            attrs = { style { flexGrow(1) } })
    }
}


@Composable
private fun Options(
    statsViewModel: StatisticsViewModel,
    attrs: AttrBuilderContext<*> = {},
) {
    HStack(attrs = {
        id("option-items-container")
        style { width(matchParent) }
        attrs()
    }) {
        Spacer()
        OptionItem(img = "icons/ic_menu_stats.svg",
            label = "Statistics",
            action = { statsViewModel.showGamesStats(GameConfig.default) })
        OptionItem(
            img = "icons/ic_menu_options.svg",
            label = "Options"
        )
    }
}

@Composable
fun OptionItem(
    img: String,
    label: String,
    action: () -> Unit = {}
) {
    val showOptionText by currentScreenSize
        .map { it > ScreenSize.MEDIUM }
        .collectAsState(false)

    val optionItemStyle: StyleBuilder.() -> Unit = {
        borderLessButton()
        textAlign("center")
        margin(4.px)
        padding(8.px)
    }

    Button(attrs = {
        style(optionItemStyle)
        onClick { action() }
    }) {
        HStack(attrs = {
            style {
                alignItems(AlignItems.Center)
            }
        }) {
            Img(src = img)
            if (showOptionText) Text(label)
        }
    }
}