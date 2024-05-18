package com.example.tipjar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tipjar.ui.screen.NewPaymentScreen
import com.example.tipjar.ui.screen.PaymentsScreen
import com.example.tipjar.ui.theme.TipJarTheme
import com.example.tipjar.util.AppScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            NavHost(
                navController = navController,
                startDestination = AppScreen.NEW_PAYMENT.name,
            ) {
                composable(route = AppScreen.NEW_PAYMENT.name) {
                    TipJarTheme {
                        NewPaymentScreen(navController = navController)
                    }
                }
                composable(route = AppScreen.PAYMENT_LIST.name) {
                    TipJarTheme {
                        PaymentsScreen(navController = navController)
                    }
                }
            }

        }
    }
}