package com.example.tipjar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tipjar.ui.screen.NewPaymentScreen
import com.example.tipjar.ui.theme.TipJarTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipJarTheme {
                NewPaymentScreen()
            }
        }
    }
}