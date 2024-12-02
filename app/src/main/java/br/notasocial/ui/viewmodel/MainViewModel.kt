package br.notasocial.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.data.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class MainUiState(
    val token: String = "",
    val role: String = ""
)

class MainViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val uiState: StateFlow<MainUiState> =
        userPreferencesRepository.currentUserData.map { userData ->
            MainUiState(token = userData.token, role = userData.role)
        }.stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5_000),
            initialValue = MainUiState()
        )

    fun logout() {
        viewModelScope.launch {
            userPreferencesRepository.logout()
        }
    }
}