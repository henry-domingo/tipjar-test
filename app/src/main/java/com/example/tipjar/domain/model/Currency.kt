package com.example.tipjar.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Currency(
    @SerializedName("code")
    @Expose
    var code: String,// USD
    @SerializedName("name")
    @Expose
    var name: String,// US Dollar
    @SerializedName("symbol")
    @Expose
    var symbol: String,// $
)
