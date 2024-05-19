package com.example.tipjar.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.tipjar.domain.model.TipHistory
import com.example.tipjar.ui.theme.TipJarTheme
import com.example.tipjar.ui.theme.compactPaddingDimensions
import com.example.tipjar.ui.theme.compactTipTypography
import com.example.tipjar.ui.widget.PaymentRow
import com.example.tipjar.util.TipShapes
import com.example.tipjar.util.getFilePath

@Composable
fun PaymentPopupScreen(
    currency: String,
    data: TipHistory,
    onDismissRequest: () -> Unit,
) {
    val imagePath = if (data.imagePath.isNotBlank()) {
        LocalContext.current.getFilePath(data.imagePath)
    } else {
        ""
    }

    //TODO shared animation
    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Surface(
            color = Color.White.copy(alpha = 0.5f),
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(compactPaddingDimensions.widePadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (imagePath.isNotBlank()) {
                    AsyncImage(
                        model = imagePath,
                        contentDescription = "image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .clip(TipShapes.medium)
                            .fillMaxWidth(0.8f)
                            .fillMaxHeight(0.5f)
                    )
                }
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = TipShapes.medium
                        )
                        .fillMaxWidth()
                ) {
                    PaymentRow(
                        currency = currency,
                        item = data,
                        hideImage = true,
                    ) {}
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                    ) {
                        Text(
                            text = stringResource(id = android.R.string.ok),
                            color = Color.White,
                            style = compactTipTypography.boldMedium
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentPopupScreenPreview() {
    TipJarTheme {
        PaymentPopupScreen(
            currency = "$",
            data = TipHistory(
                timestamp = 1611195773000,
                amount = 205.23,
                tip = 20.52,
                imagePath = "",
            ),
            onDismissRequest = {},
        )
    }
}