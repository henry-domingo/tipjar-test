package com.example.tipjar.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.tipjar.R
import com.example.tipjar.ui.theme.compactPaddingDimensions
import com.example.tipjar.ui.theme.compactTipTypography
import com.example.tipjar.ui.widget.PaymentRow
import com.example.tipjar.util.TipShapes
import com.example.tipjar.viewmodel.TipHistoryViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * Tip History list screen
 *
 * @param vm
 * @param navController
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PaymentsScreen(
    vm: TipHistoryViewModel = koinViewModel(),
    navController: NavHostController
) {
    val dataList by vm.payments.collectAsState()
    val currency by vm.currency.collectAsState()
    val showDialog by vm.showDialog.collectAsState()
    val showDatePicker by vm.showDatePicker.collectAsState()
    val dateRangeState = rememberDateRangePickerState()
    vm.filesDirectory = LocalContext.current.filesDir

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
                    if (showDatePicker.second == null) {
                        IconButton(onClick = {
                            vm.toggleDateDialog(true)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "search"
                            )
                        }
                    } else {
                        IconButton(onClick = {
                            vm.toggleDateDialog(false)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "clear"
                            )
                        }
                    }

                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
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
        if (dataList.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (showDatePicker.second != null) {
                    Text(
                        modifier = Modifier
                            .padding(compactPaddingDimensions.widePadding),
                        textAlign = TextAlign.Center,
                        text = stringResource(id = R.string.no_payments_search),
                        style = compactTipTypography.boldMedium
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .padding(compactPaddingDimensions.widePadding),
                        textAlign = TextAlign.Center,
                        text = stringResource(id = R.string.no_payments),
                        style = compactTipTypography.boldMedium
                    )
                    Icon(
                        imageVector = Icons.Outlined.AddCircle,
                        contentDescription = "add",
                        Modifier
                            .clickable {
                                navController.navigateUp()
                            }
                            .size(64.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(innerPadding)
            ) {
                items(
                    items = dataList,
                    key = { data ->
                        data.timestamp
                    },
                ) { data ->
                    SwipeBox(
                        onDelete = {
                            vm.onDeletePayment(data)
                        },
                        modifier = Modifier.animateItemPlacement()
                    ) {
                        PaymentRow(
                            item = data,
                            currency = currency,
                        ) {
                            vm.toggleItemDialog(true, it)
                        }
                    }
                }
            }
        }

        if (showDialog.first) {
            PaymentPopupScreen(
                currency = currency,
                data = showDialog.second!!,
            ) {
                vm.toggleItemDialog(false)
            }
        }

        if (showDatePicker.first) {
            Dialog(onDismissRequest = {
                vm.toggleDateDialog(false)
            }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .padding(compactPaddingDimensions.largePadding),
                    shape = TipShapes.large,
                ) {
                    DateRangePicker(
                        modifier = Modifier.weight(1f),
                        state = dateRangeState,
                        title = {},
                        headline = {
                            Text(
                                modifier = Modifier.padding(start = compactPaddingDimensions.largePadding),
                                text = stringResource(id = R.string.pick_date)
                            )
                        },
                    )
                    Button(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(compactPaddingDimensions.largePadding),
                        onClick = {
                            vm.toggleDateDialog(
                                false,
                                dateRangeState.selectedStartDateMillis,
                                dateRangeState.selectedEndDateMillis
                            )
                        }
                    ) {
                        Text(text = stringResource(id = R.string.search))
                    }
                }
            }
        }

        vm.onShowAllPayments(startDate = showDatePicker.second, endDate = showDatePicker.third)
        LaunchedEffect(key1 = true, block = {
            vm.onGetCurrency()
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeBox(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    content: @Composable () -> Unit
) {
    val swipeState = rememberSwipeToDismissBoxState()

    var icon = Icons.Outlined.Add
    var alignment = Alignment.CenterStart
    var color = Color.White

    when (swipeState.dismissDirection) {
        SwipeToDismissBoxValue.EndToStart -> {
            icon = Icons.Outlined.Delete
            alignment = Alignment.CenterEnd
            color = MaterialTheme.colorScheme.errorContainer
        }

        SwipeToDismissBoxValue.Settled -> {
            icon = Icons.Outlined.Delete
            alignment = Alignment.CenterEnd
            color = MaterialTheme.colorScheme.errorContainer
        }

        SwipeToDismissBoxValue.StartToEnd -> {}
    }

    SwipeToDismissBox(
        modifier = modifier.animateContentSize(),
        state = swipeState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            Box(
                contentAlignment = alignment,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
            ) {
                Icon(
                    modifier = Modifier.minimumInteractiveComponentSize(),
                    imageVector = icon, contentDescription = null
                )
            }
        }
    ) {
        content()
    }

    when (swipeState.currentValue) {
        SwipeToDismissBoxValue.EndToStart -> {
            onDelete()
        }

        SwipeToDismissBoxValue.StartToEnd -> {}

        SwipeToDismissBoxValue.Settled -> {
        }
    }
}