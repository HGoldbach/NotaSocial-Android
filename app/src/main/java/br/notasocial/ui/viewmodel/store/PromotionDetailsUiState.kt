package br.notasocial.ui.viewmodel.store

import br.notasocial.data.model.StoreDb.PromotionDb

data class PromotionDetailsUiState(
    val products: List<Pair<String, String>> = emptyList(),
    val promotion: PromotionDb = PromotionDb()
)
