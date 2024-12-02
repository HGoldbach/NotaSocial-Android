package br.notasocial.ui.viewmodel.customer.userprofile

data class AccountUiState(
    var firstName: String = "",
    var lastName: String = "",
    var password: String = "",
    var confirmPassword: String = "",
    var email: String = "",
    var isFirstNameValid: Boolean = true,
    var isLastNameValid: Boolean = true,
    var isPasswordValid: Boolean = true,
    var isConfirmPasswordValid: Boolean = true
)
