package com.example.tipjar.ui.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.stringResource
import com.example.tipjar.R
import com.example.tipjar.domain.model.TipHistory
import com.example.tipjar.ui.theme.Gray3
import com.example.tipjar.ui.theme.compactPaddingDimensions
import com.example.tipjar.ui.theme.compactTipTypography
import com.example.tipjar.util.toDateString

/**
 * Composable for payment item
 *
 * @param modifier
 * @param currency current currency
 * @param isDisplayedInRow flag to determine the weight of the tip text
 * @param item [TipHistory] instance
 */
@Composable
fun PaymentItem(
    modifier: Modifier = Modifier,
    currency: String,
    isDisplayedInRow: Boolean = true,
    item: TipHistory
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = item.timestamp.toDateString(),
            style = compactTipTypography.regularMedium,
        )
        Spacer(modifier = Modifier.height(compactPaddingDimensions.mediumPadding))
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                modifier = Modifier
                    .alignBy(LastBaseline),
                text = stringResource(id = R.string.currency_amount, currency, item.amount),
                style = compactTipTypography.boldExtraLarge
            )
            val modifierLastText = if (isDisplayedInRow) {
                Modifier
                    .alignBy(LastBaseline)
                    .weight(1.7f)
            } else {
                Modifier.alignBy(LastBaseline)
            }
            Spacer(modifier = Modifier.weight(0.5f))
            Text(
                modifier = modifierLastText,
                text = stringResource(id = R.string.currency_amount_tip, currency, item.tip),
                style = compactTipTypography.regularMedium,
                color = Gray3
            )
        }
    }
}