package br.notasocial.ui.viewmodel.customer.searchstore

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.data.model.Store
import br.notasocial.data.repository.StoreApiRepository
import br.notasocial.ui.viewmodel.customer.searchproduct.CatalogUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed interface StoreUiState {
    data class Success(val stores: List<Store>) : StoreUiState
    data object Loading : StoreUiState
    data class Error(val errorMessage: String) : StoreUiState
}

class SearchStoreViewModel(
    private val storeApiRepository: StoreApiRepository
): ViewModel() {

    var storeUiState: StoreUiState by mutableStateOf(StoreUiState.Loading)
        private set

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    init {
        getStores()
    }

    private fun getStores() {
        viewModelScope.launch {
            storeUiState = StoreUiState.Loading
            storeUiState = try {
                val response = storeApiRepository.getStores()
                if (response.isSuccessful) {
                    StoreUiState.Success(response.body()!!.stores)
                } else {
                    StoreUiState.Error("Response not successfull ${response.code()}")
                }
            } catch (e: IOException) {
                StoreUiState.Error(e.message!!)
            } catch (e: HttpException) {
                StoreUiState.Error(e.message!!)
            }
            Log.d("SearchStoreViewModel", storeUiState.toString())
        }
    }

    fun searchStore(value: String) {
        if(value.isEmpty()) return

        viewModelScope.launch {
            storeUiState = StoreUiState.Loading
            storeUiState = try {
                val response = storeApiRepository.searchStore(value)
                if (response.isSuccessful) {
                    StoreUiState.Success(response.body()!!.stores)
                } else {
                    StoreUiState.Error("Response not successfull ${response.code()}")
                }
            } catch(e: IOException) {
                StoreUiState.Error(e.message!!)
            } catch(e: HttpException) {
                StoreUiState.Error(e.message!!)
            }
        }
    }


}