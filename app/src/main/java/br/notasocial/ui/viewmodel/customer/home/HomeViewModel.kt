package br.notasocial.ui.viewmodel.customer.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.data.model.Catalog.CatalogProduct
import br.notasocial.data.model.Catalog.Category
import br.notasocial.data.repository.ProductApiRepository
import br.notasocial.data.repository.UserPreferencesRepository
import br.notasocial.ui.viewmodel.customer.signin.SignInViewModel.UiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

/*
sealed interface HomeUiState {
    data class Success(val categories: List<Category>) : HomeUiState
    data class Error(val message: String) : HomeUiState
    data object Loading : HomeUiState
}

 */

sealed interface CategoryHomeUiState {
    data class Success(val categories: List<Category>) : CategoryHomeUiState
    data class Error(val errorMessage: String) : CategoryHomeUiState
    data object Loading : CategoryHomeUiState
}

sealed interface HomeUiState {
    data class Success(val catalogProduct: CatalogProduct) : HomeUiState
    data class Error(val errorMessage: String) : HomeUiState
    data object Loading : HomeUiState
}

class HomeViewModel(
    private val productApiRepository: ProductApiRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {

    private var TAG = "HOMEVIEWMODEL"

    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    var categoryHomeUiState: CategoryHomeUiState by mutableStateOf(CategoryHomeUiState.Loading)
        private set

    var categories: List<Category> by mutableStateOf(listOf())
        private set

    var userRole: String by mutableStateOf("")
    private var token: String by mutableStateOf("")

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    sealed class UiEvent {
        object AddToListSuccess : UiEvent()
        object FavoriteSuccess : UiEvent()
        data class ShowError(val message: String) : UiEvent()
    }

    init {
        fetchData()
        getUserRole()
        getToken()
    }

    private fun fetchData() {
        viewModelScope.launch {
            getLowestPrices()
            getCategories()
        }
    }

    private fun getLowestPrices() {
        viewModelScope.launch {
            homeUiState = HomeUiState.Loading
            homeUiState = try {
                val response = productApiRepository.getLowestPrice()
                if (response.isSuccessful) {
                    HomeUiState.Success(catalogProduct = response.body()!!)
                } else {
                    HomeUiState.Error("Response not successfull ${response.code()}")
                }
            } catch (e: IOException) {
                HomeUiState.Error(e.message!!)
            } catch (e: HttpException) {
                HomeUiState.Error(e.message!!)
            }
            Log.i(TAG, "getLowestPrices: $homeUiState")
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            categoryHomeUiState = CategoryHomeUiState.Loading
            categoryHomeUiState = try {
                val response = productApiRepository.getCategories()
                if (response.isSuccessful) {
                    CategoryHomeUiState.Success(response.body()!!.categories)
                } else {
                    CategoryHomeUiState.Error("Response not successfull ${response.code()}")
                }
            } catch (e: IOException) {
                CategoryHomeUiState.Error(e.message!!)
            } catch (e: HttpException) {
                CategoryHomeUiState.Error(e.message!!)
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