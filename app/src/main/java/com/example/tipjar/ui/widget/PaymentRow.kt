package com.example.tipjar.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tipjar.R
import com.example.tipjar.domain.model.TipHistory
import com.example.tipjar.ui.theme.Gray3
import com.example.tipjar.ui.theme.TipJarTheme
import com.example.tipjar.ui.theme.compactPaddingDimensions
import com.example.tipjar.ui.theme.compactTipTypography
import com.example.tipjar.util.TipShapes
import com.example.tipjar.util.getFilePath
import com.example.tipjar.util.toDateString

@Composable
fun PaymentRow(
    currency: String,
    item: TipHistory,
    hideImage: Boolean = false,
    onClick: ((TipHistory) -> Unit),
) {
    val imagePath = if (item.imagePath.isNotBlank()) {
        LocalContext.current.getFilePath(item.imagePath)
    } else {
        ""
    }

    Row(
        modifier = Modifier
            .background(Color.White)
            .padding(compactPaddingDimensions.extraMediumPadding)
            .clickable {
                onClick(item)
            }
    ) {
        Column {
            Text(
                text = item.timestamp.toDateString(),
                style = compactTipTypography.regularMedium,
            )
            Spacer(modifier = Modifier.height(compactPaddingDimensions.mediumPadding))
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    modifier = Modifier.alignBy(LastBaseline),
                    text = stringResource(id = R.string.currency_amount, currency, item.amount),
                    style = compactTipTypography.boldExtraLarge
                )
                Spacer(modifier = Modifier.width(compactPaddingDimensions.extraLargePadding))
                Text(
                    modifier = Modifier.alignBy(LastBaseline),
                    text = stringResource(id = R.string.currency_amount_tip, currency, item.tip),
                    style = compactTipTypography.regularMedium,
                    color = Gray3
                )
            }
        }
        if (!hideImage) {
            Spacer(modifier = Modifier.weight(1f))
            if (imagePath.isNotBlank()) {
                AsyncImage(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(TipShapes.large),
                    model = imagePath,
                    contentDescription = "image"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentRowPreview() {
    TipJarTheme {
        PaymentRow(
            currency = "$",
//            hideImage = true,
            item = TipHistory(
                timestamp = 1611195773000,
                amount = 205.23,
                tip = 20.52,
                imagePath = "",
            )
        ) {}
    }
}