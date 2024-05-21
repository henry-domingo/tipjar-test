package com.example.tipjar.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class TipPaddingDimensions(
    val tinyPadding: Dp,
    val smallPadding: Dp,
    val mediumPadding: Dp,
    val extraMediumPadding: Dp,
    val largePadding: Dp,
    val extraLargePadding: Dp,
    val widePadding: Dp,
    val extraWidePadding: Dp,
)

// <600 width
val compactPaddingDimensions = TipPaddingDimensions(
    tinyPadding = 2.dp,
    smallPadding = 4.dp,
    mediumPadding = 8.dp,
    extraMediumPadding = 12.dp,
    largePadding = 16.dp,
    extraLargePadding = 24.dp,
    widePadding = 32.dp,
    extraWidePadding = 64.dp,
)

// We can define other dimensions here - like for medium-sized phones or tablets