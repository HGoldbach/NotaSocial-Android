package br.notasocial.ui.viewmodel.customer.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.data.model.Receipt.Receipt
import br.notasocial.data.model.Social.Review
import br.notasocial.data.model.User.UserResponse
import br.notasocial.data.repository.NotaSocialApiRepository
import br.notasocial.data.repository.UserApiRepository
import br.notasocial.data.repository.UserPreferencesRepository
import br.notasocial.ui.view.customer.profile.ProfileDestination
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    savedStateHandle: SavedStateHandle,
    private val userApiRepository: UserApiRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val notaSocialRepository: NotaSocialApiRepository
) : ViewModel() {

    private val userKeycloakId: String =
        checkNotNull(savedStateHandle[ProfileDestination.profileIdArg])
    private var token: String = ""
    private val TAG = "ProfileViewModel"
    private var userLoggedKeycloakId: String = ""

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    sealed class UiEvent {
        object FollowSuccess : UiEvent()
        data class ShowError(val message: String) : UiEvent()
    }

    var user: UserResponse by mutableStateOf(UserResponse())
        private set

    var isUserFollowing: Boolean by mutableStateOf(false)
        private set

    var isUserProfile: Boolean by mutableStateOf(false)
        private set

    var reviews: List<Review> by mutableStateOf(emptyList())
        private set

    var followers: List<UserResponse> by mutableStateOf(emptyList())
        private set

    var following: List<UserResponse> by mutableStateOf(emptyList())
        private set

    var receipts: List<Receipt> by mutableStateOf(listOf())
        private set

    init {
        viewModelScope.launch {
            userPreferencesRepository.currentUserData.collect { userData ->
                if (userData.token.isNotEmpty() && userData.keycloakId.isNotEmpty()) {
                    token = userData.token
                    userLoggedKeycloakId = userData.keycloakId
                    getUserProfile(token)
                    getFollowers(token)
                    getFollowing(token)
                    getReviews(token)
                    getReceipts(token)
                }
            }

        }
    }

    private fun getReceipts(token: String) {
        viewModelScope.launch {
            try {
                val response = notaSocialRepository.getReceipts(token, userKeycloakId)
                if (response.isSuccessful) {
                    receipts = response.body()!!.content
                } else {
                    Log.e("ReceiptsViewModel", "error getReceipts: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("ReceiptsViewModel", "error getReceipts: ${e.message}")
            }
        }
    }

    private fun getUserProfile(token: String) {
        viewModelScope.launch {
            try {
                val response = userApiRepository.getUserProfile(token, userKeycloakId)
                if (response.isSuccessful) {
                    user = response.body()!!
                    isUserProfile = user.keycloakId == userLoggedKeycloakId
                } else {
                    Log.e("ProfileViewModel", "Erro ao obter perfil do usuário: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Erro ao obter perfil do usuário", e)
            }
        }
    }

    private fun getReviews(token: String) {
        viewModelScope.launch {
            try {
                val response = userApiRepository.getUserReviews(token, userKeycloakId)
                if (response.isSuccessful) {
                    reviews = response.body()!!
                } else {
                    Log.e(
                        "ProfileViewModel",
                        "Erro ao obter reviews do usuário: ${response.code()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Erro ao obter reviews do usuário", e)
            }
        }
    }

    private fun getFollowers(token: String) {
        viewModelScope.launch {
            try {
                val response = userApiRepository.getUserFollowers(token, userKeycloakId)
                if (response.isSuccessful) {
                    followers = response.body()!!.users
                } else {
                    Log.e(
                        "ProfileViewModel",
                        "Erro ao obter seguidores do usuário: ${response.code()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Erro ao obter seguidores do usuário", e)
            }
            isUserFollowing = followers.any { it.keycloakId == userLoggedKeycloakId }
        }

    }

    private fun getFollowing(token: String) {
        viewModelScope.launch {
            try {
                val response = userApiRepository.getUserFollowing(token, userKeycloakId)
                if (response.isSuccessful) {
                    following = response.body()!!.users
                } else {
                    Log.e(
                        "ProfileViewModel",
                        "Erro ao obter usuários que seguem o usuário: ${response.code()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Erro ao obter usuários que seguem o usuário", e)
            }
        }
    }

    fun followUser() {
        viewModelScope.launch {
            try {
                val response = userApiRepository.followUser(token, userKeycloakId)
                if (response.isSuccessful) {
                    _uiEvent.emit(UiEvent.FollowSuccess)
                } else {
                    _uiEvent.emit(UiEvent.ShowError("Erro ao seguir usuário"))
                }
            } catch (e: Exception) {
                _uiEvent.emit(UiEvent.ShowError("Erro ao seguir usuário"))
            }

        }
    }
}