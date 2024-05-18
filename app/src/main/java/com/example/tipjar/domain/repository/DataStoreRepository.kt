package com.example.tipjar.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun putString(key: String, value: String)
    fun getString(key: String, defaultValue: String = ""): Flow<String>
}