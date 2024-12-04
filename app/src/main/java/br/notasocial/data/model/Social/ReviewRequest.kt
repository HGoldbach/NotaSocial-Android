package br.notasocial.data.model.Social

data class ReviewRequest(
    val rating: Int,
    val comment: String,
    val productId: String,
    val storeId: String
)
