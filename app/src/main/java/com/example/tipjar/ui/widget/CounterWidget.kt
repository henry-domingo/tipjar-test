package com.example.tipjar.ui.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipjar.ui.theme.Gray2
import com.example.tipjar.ui.theme.Orange
import com.example.tipjar.ui.theme.compactTipTypography

/**
 * Composable function for the counter widget.
 *
 * @param count current value of the counter
 * @param label label for the counter
 * @param increment callback function to increment the counter
 * @param decrement callback function to decrement the counter
 * @receiver
 * @receiver
 */
@Composable
fun CounterWidget(
    count: Int,
    label: String = "",
    increment: (() -> Unit),
    decrement: (() -> Unit),
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            fontSize = 16.sp,
            text = label,
            fontWeight = FontWeight.Bold
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedButton(
                border = BorderStroke(1.dp, Gray2),
                onClick = {
                    increment()
                }
            ) {
                Text("+", style = compactTipTypography.boldXXL, color = Orange)
            }
            Text(
                modifier = Modifier.weight(1f),
                style = compactTipTypography.boldXXL,
                textAlign = TextAlign.Center,
                text = "$count"
            )
            OutlinedButton(
                border = BorderStroke(1.dp, Gray2),
                enabled = count > 1,
                onClick = {
                    decrement()
                }
            ) {
                Text(
                    "-", style = compactTipTypography.boldXXL,
                    color = Orange
                )
            }
        }
    }
}