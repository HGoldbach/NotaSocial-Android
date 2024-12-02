package br.notasocial.data.model.Catalog

import com.google.gson.annotations.SerializedName

data class CatalogCategory(
    @SerializedName("content")
    val categories: List<Category>
)
