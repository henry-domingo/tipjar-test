package com.example.tipjar.domain.repository

import kotlinx.coroutines.flow.Flow

/**
 * Repository for Data Store
 *
 * @constructor Create empty Data store repository
 */
interface DataStoreRepository {
    /**
     * Put string to Data Store
     *
     * @param key
     * @param value
     */
    suspend fun putString(key: String, value: String)

    /**
     * Get string from Data Store
     *
     * @param key
     * @param defaultValue
     * @return
     */
    fun getString(key: String, defaultValue: String = ""): Flow<String>
}