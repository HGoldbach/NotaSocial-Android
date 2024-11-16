package br.notasocial.data.model.Social

data class Review(
    val id: Int,
    val product: Product,
    val stars: Int,
    val comment: String,
)
