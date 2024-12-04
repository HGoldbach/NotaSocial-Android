package br.notasocial.data.model.Social

import com.google.gson.annotations.SerializedName

data class CommentResponse(
    @SerializedName("content")
    val comments: List<CommentResponseItem> = emptyList()
)
