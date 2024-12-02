package br.notasocial.data.model.Social

data class ReviewRequest(
    val productId: String,
    val storeId: String,
    val comment: String,
    val rating: Int,
)
