package com.example.tipjar.ui.widget

import androidx.annotation.StringRes
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

enum class BorderedTextFieldType {
    AMOUNT,
    TIP
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BorderedNumberField(
    textValue: Double,
    @StringRes errorValue: Int,
    label: String = "",
    leadingText: String = "",
    trailingText: String = "",
    hint: String = "",
    onValueChange: ((Double) -> Unit),
) {
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
                value = if (textValue == 0.0) "" else "$textValue",
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
                    onValueChange(it.toDoubleOrNull() ?: 0.0)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedContainerColor = White,
                    focusedContainerColor = White,
                ),
                isError = errorValue != -1,
            )
            Text(
                modifier = Modifier.defaultMinSize(minWidth = 12.dp),
                style = compactTipTypography.regularExtraLarge,
                text = trailingText
            )
        }

        if (errorValue != -1) {
            Text(
                text = stringResource(id = errorValue),
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