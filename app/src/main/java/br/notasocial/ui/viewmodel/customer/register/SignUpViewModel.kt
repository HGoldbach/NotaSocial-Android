package br.notasocial.ui.viewmodel.customer.register

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.data.model.Auth.CustomerAuthRequest
import br.notasocial.data.model.Auth.StoreAuthRequest
import br.notasocial.data.repository.AuthApiRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class SignUpViewModel(
    private val authApiRepository: AuthApiRepository
) : ViewModel() {

    var uiState by mutableStateOf(SignUpUiState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    sealed class UiEvent {
        object RegistrationSuccess : UiEvent()
        data class ShowError(val message: String) : UiEvent()
    }

    fun onProfileTypeChange(profileType: ProfileType) {
        uiState = uiState.copy(profileType = profileType)
    }

    fun onNameChange(value: String) {
        uiState = uiState.copy(
            name = value,
            isNameValid = Validator.isValidName(value)
        )
    }

    fun onEmailChange(value: String) {
        uiState = uiState.copy(
            email = value,
            isEmailValid = Validator.isValidEmail(value)
        )
    }

    fun onCnpjChange(value: String) {
        uiState = uiState.copy(
            cnpj = value,
            isCnpjValid = Validator.isValidCnpj(value) || uiState.profileType == ProfileType.ESTABELECIMENTO
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

    private fun validateFields(): Boolean {
        val isNameValid = Validator.isValidName(uiState.name)
        val isEmailValid = Validator.isValidEmail(uiState.email)
        val isPasswordValid = Validator.isValidPassword(uiState.password)
        val isConfirmPasswordValid = Validator.isValidConfirmPassword(uiState.password, uiState.confirmPassword)
        val isCnpjValid = uiState.profileType == ProfileType.ESTABELECIMENTO && Validator.isValidCnpj(uiState.cnpj)

        uiState = uiState.copy(
            isNameValid = isNameValid,
            isEmailValid = isEmailValid,
            isPasswordValid = isPasswordValid,
            isConfirmPasswordValid = isConfirmPasswordValid,
            isCnpjValid = isCnpjValid
        )

        return when (uiState.profileType) {
            ProfileType.ESTABELECIMENTO -> isNameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid && isCnpjValid
            else -> isNameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid
        }
    }

    private suspend fun emitError(message: String) {
        _uiEvent.emit(UiEvent.ShowError(message))
    }

    private suspend fun <T> executeRegisterRequest(
        request: T,
        registerCall: suspend (T) -> Response<*>,
        errorMessage: String
    ) {
        try {
            val response = registerCall(request)
            if (response.isSuccessful) {
                _uiEvent.emit(UiEvent.RegistrationSuccess)
            } else {
                throw Exception(response.message())
            }
        } catch (e: Exception) {
            Log.e("RegisterViewModel", "Erro no cadastro", e)
            _uiEvent.emit(UiEvent.ShowError(errorMessage))
        }
    }

    fun register() {
        if (!validateFields()) {
            viewModelScope.launch { emitError("Preencha os campos corretamente.") }
            return
        }

        viewModelScope.launch {
            when (uiState.profileType) {
                ProfileType.ESTABELECIMENTO -> executeRegisterRequest(
                    StoreAuthRequest(
                        name = uiState.name,
                        cnpj = uiState.cnpj,
                        email = uiState.email,
                        password = uiState.password
                    ),
                    authApiRepository::registerStore,
                    "Erro ao cadastrar estabelecimento"
                )
                else -> executeRegisterRequest(
                    CustomerAuthRequest(
                        firstName = uiState.name.split(" ").firstOrNull() ?: "Nome",
                        lastName = uiState.name.split(" ").getOrNull(1) ?: "Sobrenome",
                        email = uiState.email,
                        password = uiState.password
                    ),
                    authApiRepository::registerCustomer,
                    "Erro ao cadastrar usuário"
                )
            }
        }
    }
}

object Validator {
    fun isValidName(name: String) = name.trim().length >= 2
    fun isValidEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun isValidPassword(password: String) =
        password.length >= 8 && password.any { it.isDigit() } && password.any { it.isLetter() }

    fun isValidConfirmPassword(password: String, confirmPassword: String) =
        confirmPassword.isNotEmpty() && password == confirmPassword

    fun isValidCnpj(cnpj: String) = cnpj.length == 14 && cnpj.all { it.isDigit() }
}
