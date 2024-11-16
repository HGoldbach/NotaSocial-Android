package br.notasocial.ui.viewmodel.buscarproduto

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.data.model.Catalog
import br.notasocial.data.model.Product
import br.notasocial.data.repository.NotaSocialApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface CatalogUiState {
    data class Success(val catalog: Catalog) : CatalogUiState
    data class Error(val errorMessage: String): CatalogUiState
    data object Loading: CatalogUiState
}

class BuscarProdutoViewModel(
    private val notaSocialRepository: NotaSocialApiRepository
) : ViewModel() {

    var catalogUiState: CatalogUiState by mutableStateOf(CatalogUiState.Loading)
        private set

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isActiveSearch = MutableStateFlow(false)
    val isActiveSearch = _isActiveSearch.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _product = MutableStateFlow(Product())
    val product = _product.asStateFlow()

    fun searchProduct(product: String) {
        if (product.isEmpty()) {
            _product.value = Product()
        } else {
            viewModelScope.launch {
                _product.value = notaSocialRepository.searchProduct(product).body()!!
            }
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun changeActiveSearch() {
        _isActiveSearch.value = !_isActiveSearch.value
    }



    init {
        getCatalog()
    }

    fun getCatalog() {
        viewModelScope.launch {
            catalogUiState = CatalogUiState.Loading
            catalogUiState = try {
                val response = notaSocialRepository.getCatalog()
                if (response.isSuccessful) {
                    CatalogUiState.Success(response.body()!!)
                } else {
                    CatalogUiState.Error("Response not successfull ${response   .code()}")
                }
            } catch (e: IOException) {
                CatalogUiState.Error(e.message!!)
            } catch (e: HttpException) {
                CatalogUiState.Error(e.message!!)
            }
            Log.d("CatalogViewModel", catalogUiState.toString())
        }
    }
}