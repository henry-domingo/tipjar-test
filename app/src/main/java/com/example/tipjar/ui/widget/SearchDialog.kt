package com.example.tipjar.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.tipjar.R
import com.example.tipjar.domain.model.Currency
import com.example.tipjar.ui.theme.compactPaddingDimensions
import com.example.tipjar.ui.theme.compactTipTypography
import com.example.tipjar.util.TipShapes

@Composable
fun SearchDialog(
    searchTerm: String = "",
    searchResult: List<Currency>,
    onCancel: () -> Unit,
    onSearch: (String) -> Unit,
    onPickCurrency: (String) -> Unit,
) {

    Dialog(
        properties = DialogProperties(dismissOnClickOutside = false),
        onDismissRequest = {
            onCancel()
        }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .padding(compactPaddingDimensions.largePadding),
            shape = TipShapes.large,
        ) {
            TextField(
                value = searchTerm,
                onValueChange = {
                    onSearch(it)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                placeholder = {
                    Text(stringResource(R.string.search_currency))
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            if (searchResult.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(
                        items = searchResult,
                        key = { data ->
                            data.name
                        },
                    ) {
                        ListItem(
                            modifier = Modifier.clickable {
                                onPickCurrency(it.symbol)
                            },
                            leadingContent = {
                                Text(
                                    modifier = Modifier.width(compactPaddingDimensions.extraWidePadding),
                                    text = it.symbol, style = compactTipTypography.boldExtraLarge
                                )
                            },
                            headlineContent = {
                                Text(text = it.code)
                            },
                            supportingContent = {
                                Text(text = it.name)
                            }
                        )
                    }
                }
            } else {
                Spacer(modifier = Modifier.heightIn(compactPaddingDimensions.largePadding))
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.currency_not_found)
                )
            }
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onCancel() },
            ) {
                Text(
                    text = stringResource(id = R.string.close),
                    style = compactTipTypography.boldMedium
                )
            }
        }
    }
}