package br.notasocial.data.model.Catalog


import com.google.gson.annotations.SerializedName

data class CatalogProduct(
    @SerializedName("content")
    val products: List<Product> = emptyList(),
)