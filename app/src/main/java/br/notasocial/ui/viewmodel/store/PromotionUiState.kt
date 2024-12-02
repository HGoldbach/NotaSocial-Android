package br.notasocial.ui.viewmodel.store

import br.notasocial.data.model.StoreDb.PromotionDb

data class PromotionUiState(
    val title: String = "",
    val dueDate: String = "",
    val store: String = "",
    val products: List<Pair<String, String>> = emptyList(),
    val promotions: List<PromotionDb> = emptyList()  // Adicione a lista de promoções
)
