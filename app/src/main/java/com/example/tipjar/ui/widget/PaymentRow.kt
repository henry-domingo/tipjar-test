package com.example.tipjar.ui.widget

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tipjar.domain.model.TipHistory
import com.example.tipjar.ui.theme.compactPaddingDimensions
import com.example.tipjar.util.TipShapes
import com.example.tipjar.util.getFilePath

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PaymentRow(
    modifier: Modifier = Modifier,
    currency: String,
    item: TipHistory,
    onClick: ((TipHistory) -> Unit),
) {
    val imagePath = if (item.imagePath.isNotBlank()) {
        LocalContext.current.getFilePath(item.imagePath)
    } else {
        ""
    }

    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(compactPaddingDimensions.extraMediumPadding)
            .clickable {
                onClick(item)
            }
    ) {
        PaymentItem(
            modifier = Modifier.weight(1f),
            currency = currency, item = item
        )
        if (imagePath.isNotBlank()) {
            AsyncImage(
                modifier = Modifier
                    .size(64.dp)
                    .clip(TipShapes.large),
                model = imagePath,
                contentScale = ContentScale.Crop,
                contentDescription = "image"
            )
        } else {
            Box(
                modifier = Modifier
                    .size(64.dp)
            )
        }
    }
}