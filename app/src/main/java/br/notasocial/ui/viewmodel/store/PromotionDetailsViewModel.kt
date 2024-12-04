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
import br.notasocial.ui.viewmodel.store.AddressViewModel.UiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class PromotionDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val storeDbRepository: StoreDbRepository,
) : ViewModel() {

    private val promotionId: Int = checkNotNull(savedStateHandle[StorePromotionDetailsDestination.promotionIdArg])

    var uiState: PromotionDetailsUiState by mutableStateOf(PromotionDetailsUiState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    sealed class UiEvent {
        object RemoveSuccess : UiEvent()
        data class ShowError(val message: String) : UiEvent()
    }
    init {
        loadPromotionsById()
    }

    private fun parseProducts(input: String): List<Pair<String, String>> {
        return input.split(",")
            .map {
                val parts = it.split(":")
                Pair(parts[0], parts[1])
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
                _uiEvent.emit(UiEvent.ShowError("Erro ao carregar promoção"))
                Log.e("PromotionViewModel", "Error loading promotions by storeId", e)
            }
        }
    }

    fun removePromotion() {
        viewModelScope.launch {
            try {
                storeDbRepository.deletePromotion(promotionId)
                _uiEvent.emit(UiEvent.RemoveSuccess)
            } catch (e: Exception) {
                _uiEvent.emit(UiEvent.ShowError("Erro ao remover promoção"))
                Log.e("PromotionViewModel", "Error removing promotion", e)
            }
        }
    }


}
