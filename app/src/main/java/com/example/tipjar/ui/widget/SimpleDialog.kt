package com.example.tipjar.ui.widget

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.tipjar.R
import com.example.tipjar.ui.theme.Orange
import com.example.tipjar.ui.theme.TipJarTheme

/**
 * Composable
 *
 * @param body String resource id to be displayed in the dialog
 * @param dismissText String resource id to be displayed in the negative button
 * @param onDismissRequest Callback to be invoked when the user dismisses the dialog
 * @receiver
 */
@Composable
fun SimpleDialog(
    @StringRes body: Int,
    @StringRes dismissText: Int,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        text = { Text(text = stringResource(id = body)) },
        confirmButton = {
            Button(
                onClick = onDismissRequest,
                colors = ButtonDefaults.buttonColors().copy(containerColor = Orange)
            ) {
                Text(
                    text = stringResource(id = dismissText),
                )
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun SimpleDialogPreview() {
    TipJarTheme {
        SimpleDialog(R.string.camera_rationale, R.string.got_it) {

        }
    }
}