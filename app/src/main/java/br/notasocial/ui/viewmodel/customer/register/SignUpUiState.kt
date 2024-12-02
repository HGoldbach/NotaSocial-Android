package br.notasocial.ui.viewmodel.customer.register

data class SignUpUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val cnpj: String = "",
    val profileType: ProfileType = ProfileType.USUARIO,
    val isNameValid: Boolean = true,
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val isConfirmPasswordValid: Boolean = true,
    val isCnpjValid: Boolean = true
)

enum class ProfileType {
    USUARIO,
    ESTABELECIMENTO
}