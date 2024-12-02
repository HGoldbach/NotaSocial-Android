package br.notasocial.data.model.Social

import com.google.gson.annotations.SerializedName

data class SocialFavorite(
    @SerializedName("content")
    val favorites: List<Favorite> = emptyList()
)
