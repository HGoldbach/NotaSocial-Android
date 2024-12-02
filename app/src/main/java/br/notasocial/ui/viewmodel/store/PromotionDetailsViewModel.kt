package br.notasocial.ui.viewmodel.store

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.data.repository.StoreDbRepository
import br.notasocial.ui.view.customer.product.ProductDestination
import br.notasocial.ui.view.store.StorePromotionDetailsDestination
import kotlinx.coroutines.launch

class PromotionDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val storeDbRepository: StoreDbRepository,
) : ViewModel() {

    private val promotionId: Int = checkNotNull(savedStateHandle[StorePromotionDetailsDestination.promotionIdArg])

    var uiState: PromotionDetailsUiState by mutableStateOf(PromotionDetailsUiState())
        private set

    init {
        loadPromotionsById()
    }

    private fun parseProducts(input: String): List<Pair<String, String>> {
        return input.split(",") // Dividir a string em uma lista de produtos
            .map {
                val parts = it.split(":") // Dividir cada produto em nome e descrição
                Pair(parts[0], parts[1]) // Criar um Pair de nome e descrição
            }
    }

    private fun loadPromotionsById() {
        viewModelScope.launch {
            try {
                val promotion = storeDbRepository.getPromotionById(promotionId)
                uiState = uiState.copy(
                    products = parseProducts(promotion.products),
                    promotion = promotion
                )
            } catch (e: Exception) {
                // Tratar erro, como falha ao acessar o banco de dados
                Log.e("PromotionViewModel", "Error loading promotions by storeId", e)
            }
        }
    }

    fun removePromotion() {
        viewModelScope.launch {
            try {
                storeDbRepository.deletePromotion(promotionId)
            } catch (e: Exception) {
                Log.e("PromotionViewModel", "Error removing promotion", e)
            }
        }
    }


}
