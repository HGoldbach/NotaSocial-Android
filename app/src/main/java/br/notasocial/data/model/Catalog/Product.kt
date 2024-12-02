package br.notasocial.data.model.Catalog

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("category")
    val category: Category?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("unit")
    val unit: String?,
    @SerializedName("storeId")
    val storeId: String?
)
