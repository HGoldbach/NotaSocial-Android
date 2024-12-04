package br.notasocial.ui.viewmodel.customer.searchproduct

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.data.model.Catalog.CatalogProduct
import br.notasocial.data.model.Product
import br.notasocial.data.repository.NotaSocialApiRepository
import br.notasocial.data.repository.ProductApiRepository
import br.notasocial.data.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface CatalogUiState {
    data class Success(val catalogProduct: CatalogProduct) : CatalogUiState
    data class Error(val errorMessage: String): CatalogUiState
    data object Loading: CatalogUiState
}

class SearchProductViewModel(
    private val notaSocialRepository: NotaSocialApiRepository,
    private val productApiRepository: ProductApiRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val TAG = "SearchProductViewModel"
    var catalogUiState: CatalogUiState by mutableStateOf(CatalogUiState.Loading)
        private set

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    var userRole: String by mutableStateOf("")
    private var token: String by mutableStateOf("")

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    sealed class UiEvent {
        object AddToListSuccess: UiEvent()
        object FavoriteSuccess: UiEvent()
        data class ShowError(val message: String): UiEvent()
    }

    init {
        getCatalog()
        getUserRole()
        getToken()
    }

    fun getCatalog() {
        viewModelScope.launch {
            catalogUiState = CatalogUiState.Loading
            catalogUiState = try {
                val response = notaSocialRepository.getCatalog()
                if (response.isSuccessful) {
                    CatalogUiState.Success(response.body()!!)
                } else {
                    CatalogUiState.Error("Response not successfull ${response.code()}")
                }
            } catch (e: IOException) {
                CatalogUiState.Error(e.message!!)
            } catch (e: HttpException) {
                CatalogUiState.Error(e.message!!)
            }
            Log.d("CatalogViewModel", catalogUiState.toString())
        }
    }

    fun searchProduct(value: String) {
        if(value.isEmpty()) return

        viewModelScope.launch {
            catalogUiState = CatalogUiState.Loading
            catalogUiState = try {
                val response = productApiRepository.searchProduct(value)
                if (response.isSuccessful) {
                    CatalogUiState.Success(response.body()!!)
                } else {
                    CatalogUiState.Error("Response not successfull ${response.code()}")
                }
            } catch(e: IOException) {
                CatalogUiState.Error(e.message!!)
            } catch(e: HttpException) {
                CatalogUiState.Error(e.message!!)
            }
        }
    }

    fun favoriteProduct(id: String) {
        viewModelScope.launch {
            try {
                if (userRole != "CUSTOMER") {
                    _uiEvent.emit(UiEvent.ShowError("Você não tem permissão para favoritar produtos. Crie uma conta, ou faça login!"))
                    return@launch
                }
                val response = productApiRepository.favoriteProduct(token, id)
                if (response.isSuccessful) {
                    Log.d("ProductViewModel", "Produto favoritado com sucesso")
                    _uiEvent.emit(UiEvent.FavoriteSuccess)
                } else {
                    _uiEvent.emit(UiEvent.ShowError("Erro ao favoritar produto"))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiEvent.emit(UiEvent.ShowError("Erro ao favoritar produto"))
            }
        }
    }

    private fun getUserRole() {
        viewModelScope.launch {
            userPreferencesRepository.currentUserData.collect { user ->
                userRole = user.role
            }
        }
    }

    private fun getToken() {
        viewModelScope.launch {
            userPreferencesRepository.currentUserData.collect { user ->
                token = user.token
            }
        }
    }

    fun addProductToUser(productId: String) {
        Log.i(TAG, "addProductToUser: $productId")
        viewModelScope.launch {
            if (userRole != "CUSTOMER") {
                _uiEvent.emit(UiEvent.ShowError("Você não tem permissão para adicionar produtos. Crie uma conta, ou faça login!"))
                return@launch
            }
            try {
                userPreferencesRepository.addProductId(productId)
                _uiEvent.emit(UiEvent.AddToListSuccess)
            } catch (e: Exception) {
                _uiEvent.emit(UiEvent.ShowError("Erro ao adicionar produto"))
            }
        }
    }
}

