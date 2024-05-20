package com.example.tipjar.domain.usecase

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream


class JSONAssetToObjectListUseCase {
    suspend fun <T> invoke(clazz: Class<T>, inputStream: InputStream): HashMap<String, T> {
        return withContext(Dispatchers.IO) {
            val jsonString: String
            try {
                jsonString = inputStream.bufferedReader()
                    .use {
                        it.readText()
                    }
                val type =
                    TypeToken.getParameterized(HashMap::class.java, String::class.java, clazz).type
                Gson().fromJson(jsonString, type)
            } catch (ex: Exception) {
                ex.printStackTrace()
                hashMapOf()
            }
        }
    }

}