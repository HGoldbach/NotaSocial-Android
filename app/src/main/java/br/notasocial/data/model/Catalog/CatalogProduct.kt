package br.notasocial.data.model.Catalog


import com.google.gson.annotations.SerializedName

data class Catalog(
    @SerializedName("content")
    val products: List<Any?>?,
)