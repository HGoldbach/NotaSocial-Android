package br.notasocial.ui.viewmodel.customer.userprofile

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.data.model.Auth.CustomerAuthRequest
import br.notasocial.data.model.User.UserResponse
import br.notasocial.data.repository.AuthApiRepository
import br.notasocial.data.repository.UserApiRepository
import br.notasocial.data.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class UserProfileViewModel(
    private val userApiRepository: UserApiRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val authApiRepository: AuthApiRepository
) : ViewModel() {

    var uiState by mutableStateOf(AccountUiState())
        private set

    private var token: String = ""

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    sealed class UiEvent {
        object LoginSuccess : UiEvent()
        data class ShowError(val message: String) : UiEvent()
    }

    fun onLastNameChange(value: String) {
        uiState = uiState.copy(
            lastName = value,
            isLastNameValid = Validator.isValidLastName(value)
        )
    }

    fun onFirstNameChange(value: String) {
        uiState = uiState.copy(
            firstName = value,
            isFirstNameValid = Validator.isValidFirstName(value)
        )
    }

    fun onPasswordChange(value: String) {
        uiState = uiState.copy(
            password = value,
            isPasswordValid = Validator.isValidPassword(value)
        )
    }

    fun onConfirmPasswordChange(value: String) {
        uiState = uiState.copy(
            confirmPassword = value,
            isConfirmPasswordValid = Validator.isValidConfirmPassword(uiState.password, value)
        )
    }

    init {
        viewModelScope.launch {
            userPreferencesRepository.currentUserData.collect { userData ->
                if (userData.keycloakId.isNotEmpty() && userData.token.isNotEmpty()) {
                    token = userData.token
                    getUserProfile(userData.token, userData.keycloakId)
                }
            }
        }

    }

    fun updateUserProfile() {
        viewModelScope.launch {
            try {
                val customer = if (uiState.password.isNotEmpty()) {
                    CustomerAuthRequest(
                        firstName = uiState.firstName.ifEmpty { user.firstName },
                        lastName = uiState.lastName.ifEmpty { user.lastName },
                    )
                } else {
                    CustomerAuthRequest(
                        firstName = uiState.firstName.ifEmpty { user.firstName },
                        lastName = uiState.lastName.ifEmpty { user.lastName },
                        password = uiState.password
                    )
                }

               val response = authApiRepository.updateCustomer(token, customer)
                if (response.isSuccessful) {
                    Log.d("UserProfileViewModel", "Perfil do usuário atualizado com sucesso")
                } else {
                    Log.e("UserProfileViewModel", "Erro ao atualizar perfil do usuário: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("UserProfileViewModel", "Erro ao atualizar perfil do usuário", e)
            }
        }
    }

    var user: UserResponse by mutableStateOf(UserResponse())
        private set

    private fun getUserProfile(token: String, keycloakId: String) {
        viewModelScope.launch {
            try {
                val response = userApiRepository.getUserProfile(token, keycloakId)
                if (response.isSuccessful) {
                    user = response.body()!!
                } else {
                    Log.e(
                        "UserProfileViewModel",
                        "Erro ao obter perfil do usuário: ${response.code()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("UserProfileViewModel", "Erro ao obter perfil do usuário", e)
            }
        }
    }

//    private fun validateFields(): Boolean {
//        val isFirstNameValid = Validator.isValidFirstName(uiState.firstName)
//
//        val isPasswordValid = br.notasocial.ui.viewmodel.customer.register.Validator.isValidPassword(uiState.password)
//        val isConfirmPasswordValid = br.notasocial.ui.viewmodel.customer.register.Validator.isValidConfirmPassword(uiState.password, uiState.confirmPassword)
//        val isCnpjValid = uiState.profileType == ProfileType.ESTABELECIMENTO && br.notasocial.ui.viewmodel.customer.register.Validator.isValidCnpj(uiState.cnpj)
//
//        uiState = uiState.copy(
//            isNameValid = isNameValid,
//            isEmailValid = isEmailValid,
//            isPasswordValid = isPasswordValid,
//            isConfirmPasswordValid = isConfirmPasswordValid,
//            isCnpjValid = isCnpjValid
//        )
//
//        return when (uiState.profileType) {
//            ProfileType.ESTABELECIMENTO -> isNameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid && isCnpjValid
//            else -> isNameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid
//        }
//    }

}

object Validator {
    fun isValidFirstName(name: String) = name.trim().length >= 2
    fun isValidLastName(name: String) = name.trim().length >= 2
    fun isValidEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun isValidPassword(password: String) =
        password.length >= 8 && password.any { it.isDigit() } && password.any { it.isLetter() }

    fun isValidConfirmPassword(password: String, confirmPassword: String) =
        confirmPassword.isNotEmpty() && password == confirmPassword
}