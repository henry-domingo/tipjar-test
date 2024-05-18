package com.example.tipjar.ui.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipjar.ui.theme.Gray2
import com.example.tipjar.ui.theme.Orange
import com.example.tipjar.ui.theme.compactTipTypography
import com.example.tipjar.viewmodel.NewPaymentViewModel

@Composable
fun CounterWidget(
    label: String = "",
    vm: NewPaymentViewModel,
) {
    val peopleCount by vm.peopleCount.collectAsState()

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
                    vm.incrementPeople()
                }
            ) {
                Text("+", style = compactTipTypography.boldXXL, color = Orange)
            }
            Text(
                modifier = Modifier.weight(1f),
                style = compactTipTypography.boldXXL,
                textAlign = TextAlign.Center,
                text = "$peopleCount"
            )
            OutlinedButton(
                border = BorderStroke(1.dp, Gray2),
                enabled = peopleCount > 1,
                onClick = {
                    vm.decrementPeople()
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

//@Preview
//@Composable
//fun CounterWidgetPreview() {
//    TipJarTheme {
//        CounterWidget(label = "Sample label")
//    }
//}