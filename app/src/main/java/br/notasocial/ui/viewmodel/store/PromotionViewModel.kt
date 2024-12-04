package br.notasocial.ui.viewmodel.store

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.data.model.StoreDb.PromotionDb
import br.notasocial.data.repository.StoreDbRepository
import br.notasocial.data.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class PromotionViewModel(
    private val storeDbRepository: StoreDbRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private var keycloakId: String by mutableStateOf("")
    var uiState: PromotionUiState by mutableStateOf(PromotionUiState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    sealed class UiEvent {
        object PromotionSuccess : UiEvent()
        data class ShowError(val message: String) : UiEvent()
    }

    init {
        viewModelScope.launch {
            userPreferencesRepository.currentUserData.collect { userData ->
                if (userData.keycloakId.isNotEmpty()) {
                    keycloakId = userData.keycloakId
                    loadPromotionsByStoreId(keycloakId)
                }
            }

        }
    }

    private fun loadPromotionsByStoreId(storeId: String) {
        viewModelScope.launch {
            try {
                val promotions = storeDbRepository.getPromotionsByStoreId(storeId)
                uiState = uiState.copy(promotions = promotions)
            } catch (e: Exception) {
                _uiEvent.emit(UiEvent.ShowError("Erro ao carregar promoções"))
                Log.e("PromotionViewModel", "Error loading promotions by storeId", e)
            }
        }
    }

    fun onProductNameChange(index: Int, name: String) {
        val updatedProducts = uiState.products.toMutableList()
        updatedProducts[index] = updatedProducts[index].copy(first = name) // Atualiza o nome
        uiState = uiState.copy(products = updatedProducts)
    }

    fun onProductPriceChange(index: Int, price: String) {
        val updatedProducts = uiState.products.toMutableList()
        updatedProducts[index] = updatedProducts[index].copy(second = price) // Atualiza o preço
        uiState = uiState.copy(products = updatedProducts)
    }

    fun onTitleChange(title: String) {
        uiState = uiState.copy(title = title)
    }

    fun onDueDateChange(dueDate: String) {
        uiState = uiState.copy(dueDate = dueDate)
    }

    fun onStoreChange(store: String) {
        uiState = uiState.copy(store = store)
    }

    fun onAddProduct() {
        val newProduct = Pair("", "")
        uiState = uiState.copy(
            products = uiState.products + newProduct
        )
    }

    fun savePromotion() {
        // Validação dos dados
        if (uiState.title.isBlank() || uiState.store.isBlank()) {
            return
        }

        val promotionData = PromotionDb(
            title = uiState.title,
            validity = uiState.dueDate,
            establishment = uiState.store,
            storeId = keycloakId,
            products = serializeProducts(uiState.products)
        )

        viewModelScope.launch {
            try {
                storeDbRepository.insertPromotion(promotionData)
                _uiEvent.emit(UiEvent.PromotionSuccess)
            } catch (e: Exception) {
                _uiEvent.emit(UiEvent.ShowError("Erro ao salvar promoção"))
                Log.e("PromotionViewModel", "Error saving promotion", e)
            }
        }
    }

    private fun serializeProducts(products: List<Pair<String, String>>): String {
        return products.joinToString(",") { "${it.first}:${it.second}" }
    }
}