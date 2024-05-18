package com.example.tipjar.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.tipjar.R
import com.example.tipjar.ui.theme.Orange
import com.example.tipjar.ui.theme.TipJarTheme
import com.example.tipjar.ui.theme.compactPaddingDimensions
import com.example.tipjar.ui.theme.compactTipTypography
import com.example.tipjar.ui.widget.BorderedTextField
import com.example.tipjar.ui.widget.CounterWidget

@Composable
fun NewPaymentScreen(modifier: Modifier = Modifier) {
    //TODO state
    val currency = "$"
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomBar()
        }
    ) { innerPadding ->
        Content(innerPadding, currency)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    TopAppBar(
        title = {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.tipjar_logo),
                contentDescription = "logo"
            )
        },
        actions = {
            IconButton(onClick = {
                //TODO
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_history),
                    contentDescription = "history"
                )
            }
        }
    )
}

@Composable
private fun Content(innerPadding: PaddingValues, currency: String) {
    Column(
        modifier = Modifier.padding(
            start = compactPaddingDimensions.largePadding,
            end = compactPaddingDimensions.largePadding,
            top = innerPadding.calculateTopPadding(),
            bottom = innerPadding.calculateBottomPadding()
        ),
        verticalArrangement = Arrangement.spacedBy(compactPaddingDimensions.extraLargePadding)
    ) {
        BorderedTextField(
            label = stringResource(id = R.string.enter_amount),
            leadingText = currency,
            hint = "100.00"
        )
        CounterWidget(label = stringResource(id = R.string.how_many_people))
        BorderedTextField(
            label = stringResource(id = R.string.tip_percent),
            trailingText = "%",
            hint = "10"
        )
        Row {
            Text(
                style = compactTipTypography.boldMedium,
                text = stringResource(id = R.string.total_tip)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                style = compactTipTypography.boldMedium,
                text = "$20.00"//TODO
            )
        }
        Row {
            Text(
                style = compactTipTypography.boldExtraLarge,
                text = stringResource(id = R.string.per_person)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                style = compactTipTypography.boldExtraLarge,
                text = "$10.00"//TODO
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                colors = CheckboxDefaults.colors(
                    checkedColor = Orange,
                ),
                checked = true,
                onCheckedChange = {
                    //TODO
                })
            Text(
                style = compactTipTypography.regularMedium,
                text = stringResource(id = R.string.take_photo_receipt)
            )
        }
    }
}

@Composable
private fun BottomBar() {
    BottomAppBar(
    ) {

        Row(
            modifier = Modifier.padding(horizontal = compactPaddingDimensions.largePadding),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Orange),
                onClick = {
                    /*TODO*/
                }) {
                Text(
                    style = compactTipTypography.regularMedium,
                    text = stringResource(id = R.string.save_payment)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewPaymentScreenPreview() {
    TipJarTheme {
        NewPaymentScreen()
    }
}