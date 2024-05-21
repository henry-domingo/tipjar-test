package com.example.tipjar.util

object Constants {
    const val SP_CURRENCY_KEY = "currency_key"
    const val DEFAULT_CURRENCY = "$"
    const val ASSET_CURRENCY_JSON_FILENAME = "currencies.json"
}

/**
 * App screen enum for navigation
 *
 * @constructor Create empty App screen
 */
enum class AppScreen() {
    NEW_PAYMENT,
    PAYMENT_LIST
}