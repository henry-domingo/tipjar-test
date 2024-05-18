package com.example.tipjar.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.tipjar.R
import com.example.tipjar.domain.model.TipHistory
import com.example.tipjar.ui.theme.TipJarTheme
import com.example.tipjar.ui.theme.compactTipTypography
import com.example.tipjar.ui.widget.PaymentRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentsScreen() {
    //TODO
    val dataList = listOf(
        TipHistory(
            timestamp = 1611195773000,
            amount = 205.23,
            tip = 20.52,
            imagePath = "",
        ),
        TipHistory(
            timestamp = 1610590973000,
            amount = 105.23,
            tip = 10.52,
            imagePath = "",
        ),
        TipHistory(
            timestamp = 1610158973000,
            amount = 405.23,
            tip = 40.52,
            imagePath = "",
        )
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = compactTipTypography.regularMedium,
                        text = stringResource(id = R.string.saved_payments).uppercase()
                    )
                },
                actions = {
                    IconButton(onClick = {
                        //TODO
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "history"
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        //TODO
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "back"
                        )
                    }
                },
            )

        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(dataList) { data ->
                PaymentRow(data)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentsScreenPreview() {
    TipJarTheme {
        PaymentsScreen()
    }
}