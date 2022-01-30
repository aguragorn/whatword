package com.aguragorn.whatword.web.app

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.AttrBuilderContext
import org.jetbrains.compose.web.dom.ContentBuilder
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLDivElement

@Composable
fun Spacer() {
    Div(attrs = {
        style {
            flexGrow(1)
            minWidth(0.px)
            minHeight(0.px)
        }
    })
}


@Composable
fun Card(attrs: AttrBuilderContext<*> = {}, content: @Composable () -> Unit) {
    Div(
        attrs = {
            classes("card")
            attrs()
        }
    ) {
        content()
    }
}

@Composable
fun VStack(attrs: AttrBuilderContext<*> = {}, content: ContentBuilder<HTMLDivElement>) {
    Div(attrs = {
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            width(fitContent)
            height(fitContent)
        }
        attrs()
    }, content = content)
}

@Composable
fun HStack(attrs: AttrBuilderContext<*> = {}, content: ContentBuilder<HTMLDivElement>) {
    Div(attrs = {
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Row)
            width(fitContent)
            height(fitContent)
        }
        attrs()
    }, content = content)
}