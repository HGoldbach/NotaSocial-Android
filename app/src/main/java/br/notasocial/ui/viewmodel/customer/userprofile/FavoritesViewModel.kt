package br.notasocial.ui.viewmodel.customer.userprofile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.data.model.Social.Favorite
import br.notasocial.data.repository.ProductApiRepository
import br.notasocial.data.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class FavoritesUiState {
    data class Success(val favorites: List<Favorite>) : FavoritesUiState()
    data class Error(val message: String) : FavoritesUiState()
    data object Loading : FavoritesUiState()
}

class FavoritesViewModel(
    private val productApiRepository: ProductApiRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val TAG = "FavoritesViewModel"
    var uiState: FavoritesUiState by mutableStateOf(FavoritesUiState.Loading)
        private set

    private var token: String = ""
    private var keycloakId: String = ""

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    sealed class UiEvent {
        object RemoveFavoriteSuccess: UiEvent()
        data class ShowError(val message: String): UiEvent()
    }

    init {
        viewModelScope.launch {
            userPreferencesRepository.currentUserData.collect { userData ->
                if (userData.keycloakId.isNotEmpty() && userData.token.isNotEmpty()) {
                    getFavorites(userData.token, userData.keycloakId)
                    token = userData.token
                    keycloakId = userData.keycloakId
                }
            }
        }
    }

    fun onRemoveFavorite(productId: String) {
        viewModelScope.launch {
            try {
                val response = productApiRepository.removeFavorite(token, productId)
                if (response.isSuccessful) {
                    _uiEvent.emit(UiEvent.RemoveFavoriteSuccess)
                    getFavorites(token, keycloakId)
                } else {
                    _uiEvent.emit(UiEvent.ShowError("Erro ao remover favorito"))
                }
            } catch (e: Exception) {
               _uiEvent.emit(UiEvent.ShowError("Erro ao remover favorito"))
            }

        }
    }


    private fun getFavorites(token: String, keycloakId: String) {
        viewModelScope.launch {
            uiState = FavoritesUiState.Loading
            uiState = try {
                val response = productApiRepository.getFavorites(token, keycloakId)
                if (response.isSuccessful) {
                    FavoritesUiState.Success(response.body()!!.favorites)
                } else {
                    FavoritesUiState.Error(response.message())
                }
            } catch (e: IOException) {
                FavoritesUiState.Error(e.message ?: "IOException")
            } catch (e: HttpException) {
                FavoritesUiState.Error(e.message ?: "HttpException")
            }
        }
    }

}