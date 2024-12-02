package br.notasocial.data.model.Social

data class ShoppingListRequest(
    val products: List<ShoppingListItem>? = null
)

data class ShoppingListItem(
    val productId: String,
    val quantity: Int = 1
)