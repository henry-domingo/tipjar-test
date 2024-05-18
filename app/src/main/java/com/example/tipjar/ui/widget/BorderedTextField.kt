package com.example.tipjar.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.tipjar.ui.theme.Gray
import com.example.tipjar.ui.theme.Gray1
import com.example.tipjar.ui.theme.compactTipTypography
import com.example.tipjar.util.TipShapes
import com.example.tipjar.viewmodel.NewPaymentViewModel

enum class BorderedTextFieldType {
    AMOUNT,
    TIP
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BorderedTextField(
    vm: NewPaymentViewModel,
    type: BorderedTextFieldType,
    label: String = "",
    leadingText: String = "",
    trailingText: String = "",
    hint: String = ""
) {
    //states
    val textState by if (type == BorderedTextFieldType.AMOUNT) {
        vm.amount.collectAsState()
    } else {
        vm.tipPercent.collectAsState()
    }
    val errorState by if (type == BorderedTextFieldType.AMOUNT) {
        vm.amountError.collectAsState()
    } else {
        vm.tipError.collectAsState()
    }

    Column(
        modifier = Modifier.background(White),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = label,
            style = compactTipTypography.boldMedium
        )
        Row(
            modifier = Modifier
                .border(width = 2.dp, color = Gray, shape = TipShapes.extraLarge)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.defaultMinSize(minWidth = 12.dp),
                text = leadingText,
                style = compactTipTypography.regularExtraLarge
            )
            TextField(
                value = if (textState == 0.0) "" else "$textState",
                modifier = Modifier.weight(1f),
                textStyle = compactTipTypography.boldXXL,
                singleLine = true,
                placeholder = {
                    Text(
                        style = compactTipTypography.boldXXL,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = hint,
                        color = Gray1
                    )
                },
                onValueChange = {
                    if (it.contains("..")) return@TextField
                    if (type == BorderedTextFieldType.AMOUNT) {
                        vm.updateAmount(it.toDoubleOrNull() ?: 0.0)
                    } else {
                        vm.updateTipPercent(it.toDoubleOrNull() ?: 0.0)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedContainerColor = White,
                    focusedContainerColor = White,
                ),
                isError = errorState != -1,
            )
            Text(
                modifier = Modifier.defaultMinSize(minWidth = 12.dp),
                style = compactTipTypography.regularExtraLarge,
                text = trailingText
            )
        }

        if (errorState != -1) {
            Text(
                text = stringResource(id = errorState),
                style = compactTipTypography.regularMedium,
                color = Color.Red
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun BorderedTextFieldPreview() {
//    TipJarTheme {
//        BorderedTextField(
//            label = "Label", leadingText = "$", trailingText = "%", hint = "100.00"
//        )
//    }
//}