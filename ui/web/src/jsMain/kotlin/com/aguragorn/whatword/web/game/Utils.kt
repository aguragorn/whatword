package com.aguragorn.whatword.web.game

import com.aguragorn.whatword.web.app.utils.ScreenSize
import com.aguragorn.whatword.web.app.utils.currentScreenSize
import com.aguragorn.whatword.web.app.utils.getScreenSize
import com.aguragorn.whatword.web.app.utils.matchParent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.web.css.CSSNumericValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.px

fun gameWidth(): CSSNumericValue<out CSSUnit> {
    return if (getScreenSize() == ScreenSize.SMALL) matchParent else 480.px
}

fun currentGameWidth(): Flow<CSSNumericValue<out CSSUnit>> {
    return currentScreenSize.map { gameWidth() }
}