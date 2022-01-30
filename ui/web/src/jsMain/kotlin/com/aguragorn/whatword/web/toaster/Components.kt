package com.aguragorn.whatword.web.toaster

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.aguragorn.whatword.toaster.ToasterViewModel
import com.aguragorn.whatword.toaster.model.Message
import com.aguragorn.whatword.web.app.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.keywords.auto
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text

@Composable
fun Toaster(toaster: ToasterViewModel) {
    val messages = toaster.messages.collectAsState()

    HStack(attrs = {
        style {
            width(100.percent)
            position(Position.Absolute)
        }
    }) {
        Spacer()
        VStack {
            for (message in messages.value.reversed()) {
                val (iconUrl, bgColor) = when (message.type) {
                    Message.Type.INFO -> "icons/ic_toast_info.svg" to "#1E88E5"
                    Message.Type.SUCCESS -> "icons/ic_toast_success.svg" to "#66BB6A"
                    Message.Type.WARNING -> "icons/ic_toast_warn.svg" to "#FFCA28"
                    Message.Type.ERROR -> "icons/ic_toast_error.svg" to "#ef5350"
                }

                Card(
                    attrs = {
                        style {
                            borderRadius(16.px)
                            padding(8.px)
                            height(fitContent)
                            backgroundColor(Color(bgColor))
                        }
                    }
                ) {
                    HStack(attrs = {
                        style {
                            alignItems(AlignItems.Center)
                            opacity(60.percent)
                        }
                    }) {
                        Img(src = iconUrl,
                            attrs = {
                                style {
                                    width(auto)
                                    height(1.6.em)
                                    marginRight(2.px)
                                }
                            })
                        Text(message.text)
                    }
                }
            }
        }
        Spacer()
    }
}