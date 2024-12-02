package br.notasocial.data.model.Receipt

import com.google.gson.annotations.SerializedName

data class Receipt(
    @SerializedName("scannedAt")
    val scannedAt: List<Int> = emptyList(),
    @SerializedName("items")
    val items: List<Item> = emptyList(),
    @SerializedName("totalValue")
    val totalValue: Double = 0.0,
)
