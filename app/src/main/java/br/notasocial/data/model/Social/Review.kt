package br.notasocial.data.model.Social

import br.notasocial.data.model.Catalog.Product
import br.notasocial.data.model.User.UserResponse
import com.google.gson.annotations.SerializedName

//data class Review(
//    val id: Int,
//    val product: Product?,
//    val username: String = "",
//    val date: String = "",
//    val stars: Int,
//    val comment: String,
//)

data class Review(
    @SerializedName("id")
    val id: String,
    @SerializedName("product")
    val product: Product?,
    @SerializedName("user")
    val user: UserResponse,
    @SerializedName("review")
    val review: String,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("approved")
    val approved: Boolean? = null,
    @SerializedName("createdAt")
    val createdAt: String,
)
