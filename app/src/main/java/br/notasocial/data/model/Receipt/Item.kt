package br.notasocial.data.model.Receipt

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class Item(
    @SerializedName("name")
    val name: String,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("code")
    val code: String,
    @SerializedName("totalValue")
    val totalValue: Double,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("unitValue")
    val unitValue: Double,
)
