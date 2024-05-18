package com.example.tipjar.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipjar.R
import com.example.tipjar.domain.model.TipHistory
import com.example.tipjar.ui.theme.Gray3
import com.example.tipjar.ui.theme.TipJarTheme
import com.example.tipjar.ui.theme.compactPaddingDimensions
import com.example.tipjar.ui.theme.compactTipTypography
import com.example.tipjar.util.toDateString

@Composable
fun PaymentRow(item: TipHistory) {
    val currency = "$"//TODO
    Row(
        modifier = Modifier.padding(compactPaddingDimensions.extraMediumPadding)
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
        Spacer(modifier = Modifier.weight(1f))
        //TODO
        Image(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(15.dp)),
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "image"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentRowPreview() {
    TipJarTheme {
        PaymentRow(
            TipHistory(
                timestamp = 1611195773000,
                amount = 205.23,
                tip = 20.52,
                imagePath = "",
            )
        )
    }
}