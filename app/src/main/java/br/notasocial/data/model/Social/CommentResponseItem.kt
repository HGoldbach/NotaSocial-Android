package br.notasocial.data.model.Social

import br.notasocial.data.model.User.UserResponse
import com.google.gson.annotations.SerializedName

data class CommentResponseItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("productId")
    val productId: String,
    @SerializedName("storeId")
    val storeId: String,
    @SerializedName("reviewId")
    val reviewId: String,
    @SerializedName("user")
    val user: UserResponse,
    @SerializedName("text")
    val text: String,
    @SerializedName("parentCommentId")
    val parentCommentId: String,
    @SerializedName("createdAt")
    val createdAt: String,
)
