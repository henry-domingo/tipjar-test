package com.example.tipjar.util

object Constants {
    const val SP_CURRENCY_KEY = "currency_key"
    const val DEFAULT_CURRENCY = "$"
}

enum class AppScreen() {
    NEW_PAYMENT,
    PAYMENT_LIST
}

enum class SharedElementKey() {
    IMAGE,
    CONTENT,
}