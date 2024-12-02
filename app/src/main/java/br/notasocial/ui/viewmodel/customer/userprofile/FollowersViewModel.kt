package br.notasocial.ui.viewmodel.customer.userprofile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.data.model.User.UserResponse
import br.notasocial.data.model.User.UserSocial
import br.notasocial.data.repository.UserApiRepository
import br.notasocial.data.repository.UserPreferencesRepository
import kotlinx.coroutines.launch

class FollowersViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userApiRepository: UserApiRepository
) : ViewModel() {


    var user: List<UserResponse> by mutableStateOf(emptyList())
        private set

    private var token: String = ""

    init {
        viewModelScope.launch {
            userPreferencesRepository.currentUserData.collect { userData ->
                if (userData.token.isNotEmpty() && userData.keycloakId.isNotEmpty()) {
                    token = userData.token
                    getFollowers(token)
                }
            }
        }
    }

    private fun getFollowers(token: String) {
        viewModelScope.launch {
            try {
                val response = userApiRepository.getFollowers(token)
                if (response.isSuccessful) {
                    user = response.body()!!.users
                } else {
                    Log.e("FollowersViewModel", "Erro ao obter seguidores do usuário: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("FollowersViewModel", "Erro ao obter seguidores do usuário", e)
            }
        }
    }
}