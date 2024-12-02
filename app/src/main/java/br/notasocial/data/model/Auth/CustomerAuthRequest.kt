package br.notasocial.data.model.Auth

data class CustomerAuthRequest(
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val password: String? = null,
)
