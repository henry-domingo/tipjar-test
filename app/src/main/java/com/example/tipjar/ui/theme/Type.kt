package com.example.tipjar.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

const val LINE_HEIGHT_RATIO = 1.6f

data class TipTypography(
    val regularExtraSmall: TextStyle,
    val regularSmall: TextStyle,
    val regularMedium: TextStyle,
    val regularLarge: TextStyle,
    val regularExtraLarge: TextStyle,
    val regularXXL: TextStyle,
    val boldExtraSmall: TextStyle,
    val boldSmall: TextStyle,
    val boldMedium: TextStyle,
    val boldLarge: TextStyle,
    val boldExtraLarge: TextStyle,
    val boldXXL: TextStyle,
)

// <600 width
val compactTipTypography = TipTypography(
    regularExtraSmall = getRegularTextStyle(12.sp),
    regularSmall = getRegularTextStyle(14.sp),
    regularMedium = getRegularTextStyle(16.sp),
    regularLarge = getRegularTextStyle(20.sp),
    regularExtraLarge = getRegularTextStyle(24.sp),
    regularXXL = getRegularTextStyle(32.sp),
    boldExtraSmall = getBoldTextStyle(12.sp),
    boldSmall = getBoldTextStyle(14.sp),
    boldMedium = getBoldTextStyle(16.sp),
    boldLarge = getBoldTextStyle(20.sp),
    boldExtraLarge = getBoldTextStyle(24.sp),
    boldXXL = getBoldTextStyle(32.sp),
)

fun getRegularTextStyle(fontSize: TextUnit): TextStyle {
    return getTextStyle(fontSize, FontWeight.Normal)
}

fun getBoldTextStyle(fontSize: TextUnit): TextStyle {
    return getTextStyle(fontSize, FontWeight.Bold)
}

fun getTextStyle(fontSize: TextUnit, fontWeight: FontWeight): TextStyle {
    return TextStyle(
        fontWeight = fontWeight,
        fontSize = fontSize,
        lineHeight = getLineHeightFromFontSize(fontSize),
    )
}

// Function to calculate line height dynamically based on font size
private fun getLineHeightFromFontSize(fontSize: TextUnit): TextUnit {
    return fontSize * LINE_HEIGHT_RATIO
}

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)