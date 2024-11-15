package br.notasocial.data.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("code")
    val code: String? = "",
    @SerializedName("price")
    val price: Double? = 0.0,
    @SerializedName("storeCorrelationId")
    val storeCorrelationId: String? = "",
)