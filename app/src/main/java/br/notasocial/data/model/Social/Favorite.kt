package br.notasocial.data.model.Social

import com.google.gson.annotations.SerializedName

data class Favorite(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
)
