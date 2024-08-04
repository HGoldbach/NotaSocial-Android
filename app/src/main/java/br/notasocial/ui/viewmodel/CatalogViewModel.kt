package br.notasocial.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.data.model.Catalog
import br.notasocial.data.repository.NotaSocialApiRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException


sealed interface CatalogUiState {
    data class Success(val catalog: Catalog) : CatalogUiState
    data class Error(val errorMessage: String): CatalogUiState
    data object Loading: CatalogUiState
}

class CatalogViewModel(
    private val notaSocialRepository: NotaSocialApiRepository
) : ViewModel() {

    var catalog by mutableStateOf("")
        private set

    var catalogUiState: CatalogUiState by mutableStateOf(CatalogUiState.Loading)
        private set

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