package com.example.tipjar.ui.screen

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.tipjar.domain.model.TipHistory
import com.example.tipjar.ui.theme.compactPaddingDimensions
import com.example.tipjar.ui.theme.compactTipTypography
import com.example.tipjar.ui.widget.PaymentItem
import com.example.tipjar.util.getFilePath

@OptIn(ExperimentalSharedTransitionApi::class)
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

    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Surface(
            color = Color.Transparent,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (imagePath.isNotBlank()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f)
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.onBackground),
                            model = imagePath,
                            contentDescription = "image",
                            contentScale = ContentScale.Fit,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(compactPaddingDimensions.extraMediumPadding))
                Card {
                    PaymentItem(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .padding(compactPaddingDimensions.extraMediumPadding),
                        currency = currency,
                        isDisplayedInRow = false,
                        item = data
                    )
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