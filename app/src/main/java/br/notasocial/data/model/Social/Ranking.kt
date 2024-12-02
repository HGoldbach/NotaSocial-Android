package br.notasocial.data.model.Social

import com.google.gson.annotations.SerializedName

data class Ranking(
    @SerializedName("content")
    val ranking: List<UserRanking> = emptyList()
)

data class UserRanking(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("urlImage")
    val urlImage: String,
    @SerializedName("totalReceipts")
    val totalReceipts: Int,
    @SerializedName("totalProducts")
    val totalProducts: Int,
)