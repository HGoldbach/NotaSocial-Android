package br.notasocial.data.model.Social

data class CommentRequest(
    val productId: String? = null,
    val storeId: String? = null,
    val reviewId: String? = null,
    val parentCommentId: String? = null,
    val text: String? = null,
)
