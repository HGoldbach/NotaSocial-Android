package br.notasocial.data.model.Catalog

import com.google.gson.annotations.SerializedName

data class CatalogPriceHistory(
    @SerializedName("content")
    val priceHistory: List<PriceHistory?>?,
)
