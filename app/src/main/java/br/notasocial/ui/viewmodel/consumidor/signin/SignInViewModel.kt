package br.notasocial.ui.viewmodel.consumidor.signin

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.data.model.Auth.SignInAuthRequest
import br.notasocial.data.repository.AuthApiRepository
import br.notasocial.data.repository.UserPreferencesRepository
import com.auth0.android.jwt.JWT
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SignInViewModel(
    private val authApiRepository: AuthApiRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    var uiState by mutableStateOf(SignInUiState())
        private set

    val uiPrefState: StateFlow<SignInUiState> =
        userPreferencesRepository.currentUserData.map { userData ->
            SignInUiState(token = userData.token, role = userData.role)
        }.stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5_000),
            initialValue = SignInUiState()
        )

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    sealed class UiEvent {
        object LoginSuccess: UiEvent()
        data class ShowError(val message: String): UiEvent()
    }

    fun onEmailChange(value: String) {
        uiState = uiState.copy(
            email = value,
            isEmailValid = Validator.isValidEmail(value)
        )
    }

    fun onPasswordChange(value: String) {
        uiState = uiState.copy(
            password = value,
            isPasswordValid = Validator.isValidPassword(value)
        )
    }

    private fun saveUserToken(token: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveUserToken(token)
        }
    }

    private fun saveUserRole(role: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveUserRole(role)
        }
    }

    private fun validateFields(): Boolean {
        val isEmailValid = Validator.isValidEmail(uiState.email)
        val isPasswordValid = Validator.isValidPassword(uiState.password)

        uiState = uiState.copy(
            isEmailValid = isEmailValid,
            isPasswordValid = isPasswordValid
        )

        return isEmailValid && isPasswordValid
    }

    private suspend fun emitError(message: String) {
        _uiEvent.emit(UiEvent.ShowError(message))
    }

    private fun extractRoleFromToken(token: String): String {
        val realmAccess = JWT(token).getClaim("realm_access").asObject(Map::class.java)
        val roles = realmAccess?.get("roles") as List<*>
        val role = roles.find { it == "CUSTOMER" || it == "ADMIN" || it == "STORE" }
        if (role == null) {
            return ""
        }
        return role.toString()
    }

    fun login() {
        if(!validateFields()) {
            viewModelScope.launch { emitError("Preencha os campos corretamente.") }
            return
        }

        viewModelScope.launch {
            try {
                val response = authApiRepository.signIn(
                    SignInAuthRequest(
                        username = uiState.email,
                        password = uiState.password
                    )
                )
                if(response.isSuccessful) {
                    val token = response.body()?.token
                    if (token !== null) {
                        val role = extractRoleFromToken(token)
                        saveUserToken(token)
                        saveUserRole(role)
                    }
                    Log.d("SignInViewModel", "Login bem-sucedido ${token}")
                    _uiEvent.emit(UiEvent.LoginSuccess)
                } else {
                    throw Exception(response.message())
                }
            } catch(e: Exception) {
                Log.e("SignInViewModel", "Erro no cadastro", e)
                _uiEvent.emit(UiEvent.ShowError("Erro ao fazer login. Email ou senha incorreto"))
            }
        }
    }

}



object Validator {
    fun isValidEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun isValidPassword(password: String) =
        password.length >= 8 && password.any { it.isDigit() } && password.any { it.isLetter() }
}