package br.notasocial.ui.viewmodel.customer.signin

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val token: String = "",
    val role: String = ""
)
