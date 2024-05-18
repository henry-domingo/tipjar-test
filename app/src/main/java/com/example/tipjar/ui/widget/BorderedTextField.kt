package com.example.tipjar.ui.widget

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipjar.ui.theme.Gray
import com.example.tipjar.ui.theme.TipJarTheme
import com.example.tipjar.ui.theme.compactTipTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BorderedTextField(
    label: String = "",
    leadingText: String = "",
    trailingText: String = "",
    hint: String = ""
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = label,
            style = compactTipTypography.boldMedium
        )
        Row(
            modifier = Modifier
                .border(width = 2.dp, color = Gray, shape = RoundedCornerShape(20.dp))
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.defaultMinSize(minWidth = 12.dp),
                text = leadingText,
                style = compactTipTypography.regularExtraLarge
            )
            TextField(
                modifier = Modifier.weight(1f),
                value = "",
                textStyle = compactTipTypography.boldXXL,
                singleLine = true,
                placeholder = {
                    Text(
                        style = compactTipTypography.boldXXL,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = hint
                    )
                },
                onValueChange = {
                    //TODO
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedContainerColor = White,
                    focusedContainerColor = White,
                ),
            )
            Text(
                modifier = Modifier.defaultMinSize(minWidth = 12.dp),
                style = compactTipTypography.regularExtraLarge,
                text = trailingText
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BorderedTextFieldPreview() {
    TipJarTheme {
        BorderedTextField(label = "Label", leadingText = "$", trailingText = "%", hint = "100.00")
    }
}