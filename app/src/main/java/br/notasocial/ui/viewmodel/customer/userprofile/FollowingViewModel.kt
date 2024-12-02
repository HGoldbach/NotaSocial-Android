package br.notasocial.ui.viewmodel.customer.userprofile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.data.model.User.UserResponse
import br.notasocial.data.repository.UserApiRepository
import br.notasocial.data.repository.UserPreferencesRepository
import kotlinx.coroutines.launch

class FollowingViewModel(
    private val userApiRepository: UserApiRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private var token: String = ""

    var users: List<UserResponse> by mutableStateOf(listOf())
        private set

    init {
        viewModelScope.launch {
            userPreferencesRepository.currentUserData.collect { userData ->
                if(userData.token.isNotEmpty()) {
                    token = userData.token
                    getFollowing(token)
                }
            }
        }
    }

    private fun getFollowing(token: String) {
        viewModelScope.launch {
            try {
                val response = userApiRepository.getFollowing(token)
                if (response.isSuccessful) {
                    users = response.body()!!.users
                } else {
                    Log.e("FollowingViewModel", "getFollowing: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("FollowingViewModel", "getFollowing: ${e.message}")
            }
        }
    }

}