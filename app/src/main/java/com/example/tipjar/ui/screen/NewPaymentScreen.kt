package com.example.tipjar.ui.screen

import android.Manifest
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tipjar.R
import com.example.tipjar.ui.theme.Orange
import com.example.tipjar.ui.theme.TipJarTheme
import com.example.tipjar.ui.theme.compactPaddingDimensions
import com.example.tipjar.ui.theme.compactTipTypography
import com.example.tipjar.ui.widget.BorderedTextField
import com.example.tipjar.ui.widget.BorderedTextFieldType
import com.example.tipjar.ui.widget.CounterWidget
import com.example.tipjar.ui.widget.SimpleDialog
import com.example.tipjar.util.AppScreen
import com.example.tipjar.viewmodel.NewPaymentViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun NewPaymentScreen(
    vm: NewPaymentViewModel = koinViewModel(),
    navController: NavHostController
) {
    //states
    val takeImage by vm.takeImage.collectAsState()
    val showRationale by vm.showRationale.collectAsState()

    //top bar scroll behavior
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    //permission
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Permission granted
            vm.toggleImageCapture()
        } else {
            vm.showRationale(true)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(scrollBehavior, navController)
        },
        bottomBar = {
            BottomBar(vm, takeImage, navController)
        }
    ) { innerPadding ->
        Content(
            vm,
            innerPadding,
            takeImage,
            cameraPermissionState,
            requestPermissionLauncher
        )
    }

    LaunchedEffect(key1 = true, block = {
        vm.onGetCurrency()
    })

    if (showRationale) {
        SimpleDialog(R.string.camera_rationale, R.string.got_it) {
            vm.showRationale(false)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(scrollBehavior: TopAppBarScrollBehavior, navController: NavHostController) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.tipjar_logo),
                contentDescription = "logo"
            )
        },
        actions = {
            IconButton(onClick = {
                navController.navigate(AppScreen.PAYMENT_LIST.name)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_history),
                    contentDescription = "history"
                )
            }
        }
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun Content(
    vm: NewPaymentViewModel,
    innerPadding: PaddingValues,
    takeImage: Boolean,
    cameraPermissionState: PermissionState,
    requestPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
) {
    val currency by vm.currency.collectAsState()
    val tipPerPerson by vm.perPersonTip.collectAsState()
    val totalTip by vm.totalTip.collectAsState()

    Column(
        modifier = Modifier
            .padding(
                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr) + compactPaddingDimensions.largePadding,
                end = innerPadding.calculateEndPadding(LayoutDirection.Ltr) + compactPaddingDimensions.largePadding,
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            )
            .verticalScroll(state = rememberScrollState())
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(compactPaddingDimensions.extraLargePadding)
    ) {
        BorderedTextField(
            vm = vm,
            type = BorderedTextFieldType.AMOUNT,
            label = stringResource(id = R.string.enter_amount),
            leadingText = currency,
            hint = "100.00"
        )
        CounterWidget(vm = vm, label = stringResource(id = R.string.how_many_people))
        BorderedTextField(
            vm = vm,
            type = BorderedTextFieldType.TIP,
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
                text = stringResource(id = R.string.currency_amount, currency, totalTip)
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
                text = stringResource(id = R.string.currency_amount, currency, tipPerPerson)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.clickable {
                verifyCameraPermission(
                    !takeImage,
                    cameraPermissionState,
                    requestPermissionLauncher,
                    vm
                )
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                colors = CheckboxDefaults.colors(
                    checkedColor = Orange,
                ),
                checked = takeImage,
                onCheckedChange = {
                    verifyCameraPermission(it, cameraPermissionState, requestPermissionLauncher, vm)
                },
            )
            Text(
                style = compactTipTypography.regularMedium,
                text = stringResource(id = R.string.take_photo_receipt)
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
private fun verifyCameraPermission(
    check: Boolean,
    cameraPermissionState: PermissionState,
    requestPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    vm: NewPaymentViewModel
) {
    if (check) {
        if (!cameraPermissionState.status.isGranted && cameraPermissionState.status.shouldShowRationale) {
            vm.showRationale(true)
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    } else {
        vm.toggleImageCapture()
    }
}

@Composable
private fun BottomBar(
    vm: NewPaymentViewModel,
    takeImage: Boolean,
    navController: NavHostController
) {
    val isSaving by vm.isSaving.collectAsState()

    BottomAppBar {
        Row(
            modifier = Modifier.padding(horizontal = compactPaddingDimensions.largePadding),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSaving,
                colors = ButtonDefaults.buttonColors(containerColor = Orange),
                onClick = {
                    if (takeImage) {
                        //TODO capture image first
                    } else {
                        vm.onSavePayment(navController = navController)
                    }
                }) {
                Text(
                    style = compactTipTypography.regularMedium,
                    text = stringResource(
                        id = if (isSaving)
                            R.string.please_wait
                        else
                            R.string.save_payment
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewPaymentScreenPreview() {
    TipJarTheme {
        NewPaymentScreen(navController = rememberNavController())
    }
}