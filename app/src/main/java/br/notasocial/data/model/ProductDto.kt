package br.notasocial.data.model


import br.notasocial.data.model.Catalog.Category
import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("category")
    val category: Category? = null,
    @SerializedName("image")
    val image: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("storeId")
    val storeId: String,
    @SerializedName("unit")
    val unit: String
)