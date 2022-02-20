package com.aguragorn.whatword.web.app.utils

import org.jetbrains.compose.web.css.CSSKeywordValue
import org.jetbrains.compose.web.css.CSSNumeric
import org.jetbrains.compose.web.css.keywords.CSSAutoKeyword
import org.jetbrains.compose.web.css.percent

val wrapContent: CSSAutoKeyword = CSSKeywordValue("fit-content").unsafeCast<CSSAutoKeyword>()
val matchParent: CSSNumeric = 100.percent