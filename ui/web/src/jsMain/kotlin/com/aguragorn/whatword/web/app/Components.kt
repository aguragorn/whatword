package com.aguragorn.whatword.web.app

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.AttrBuilderContext
import org.jetbrains.compose.web.dom.ContentBuilder
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLDivElement

/**
 * Expands to fill the remaining space in the parent element.
 * Will push other children to the edges.
 */
@Composable
fun Spacer(attrs: AttrBuilderContext<*> = {}) {
    Div(attrs = {
        style {
            flexGrow(1)
            minWidth(0.px)
            minHeight(0.px)
        }
        attrs()
    })
}

/**
 * A card with a default elevation
 */
@Composable
fun Card(attrs: AttrBuilderContext<*> = {}, content: @Composable () -> Unit) {
    Div(attrs = {
        classes("card")
        attrs()
    }
    ) {
        content()
    }
}

/**
 * Stacks children vertically like a column. Wraps content by default.
 */
@Composable
fun VStack(attrs: AttrBuilderContext<*> = {}, content: ContentBuilder<HTMLDivElement>) {
    Div(attrs = {
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            width(wrapContent)
            height(wrapContent)
        }
        attrs()
    }, content = content)
}

/**
 * Stacks children horizontally like a row. Wraps content by default.
 */
@Composable
fun HStack(attrs: AttrBuilderContext<*> = {}, content: ContentBuilder<HTMLDivElement>) {
    Div(attrs = {
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Row)
            width(wrapContent)
            height(wrapContent)
        }
        attrs()
    }, content = content)
}