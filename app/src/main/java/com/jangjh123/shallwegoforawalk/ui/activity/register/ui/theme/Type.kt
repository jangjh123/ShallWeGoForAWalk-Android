package com.jangjh123.shallwegoforawalk.ui.activity.register.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

val Typography = Typography(
    defaultFontFamily = notoSans,
    h1 = TextStyle(
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        letterSpacing = (-1.6).sp
    )
)
