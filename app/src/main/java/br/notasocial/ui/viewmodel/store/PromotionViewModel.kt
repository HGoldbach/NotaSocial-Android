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
import kotlinx.coroutines.launch


class PromotionViewModel(
    private val storeDbRepository: StoreDbRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private var keycloakId: String by mutableStateOf("")
    var uiState: PromotionUiState by mutableStateOf(PromotionUiState())
        private set

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
                // Tratar erro, como falha ao acessar o banco de dados
                Log.e("PromotionViewModel", "Error loading promotions by storeId", e)
            }
        }
    }

    // Função para alterar o nome do produto
    fun onProductNameChange(index: Int, name: String) {
        val updatedProducts = uiState.products.toMutableList()
        updatedProducts[index] = updatedProducts[index].copy(first = name) // Atualiza o nome
        uiState = uiState.copy(products = updatedProducts)
    }

    // Função para alterar o preço do produto
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

    // Função para salvar a promoção no banco de dados
    fun savePromotion() {
        // Validação dos dados
        if (uiState.title.isBlank() || uiState.store.isBlank()) {
            // Exibir erro ou notificação de campo obrigatório
            return
        }

        // Criação do objeto PromotionDb para salvar
        val promotionData = PromotionDb(
            title = uiState.title,
            validity = uiState.dueDate,
            establishment = uiState.store,
            storeId = keycloakId,  // Supondo que o storeId seja a loja (ou deve ser obtido de algum lugar)
            products = serializeProducts(uiState.products)  // Serializando os produtos
        )

        // Chama o repositório para salvar os dados no banco de dados
        viewModelScope.launch {
            try {
                storeDbRepository.insertPromotion(promotionData)
                // Caso tenha sucesso, pode atualizar o estado ou mostrar uma mensagem de sucesso
                // Exemplo: exibir uma mensagem de sucesso ou navegar para outra tela
            } catch (e: Exception) {
                Log.e("PromotionViewModel", "Error saving promotion", e)
                // Tratar erro, exibir mensagem de falha
                // Exemplo: exibir uma mensagem de erro
            }
        }
    }

    // Função auxiliar para serializar a lista de produtos (se necessário)
    private fun serializeProducts(products: List<Pair<String, String>>): String {
        // Transformando a lista de produtos em um formato serializado
        return products.joinToString(",") { "${it.first}:${it.second}" }
    }
}