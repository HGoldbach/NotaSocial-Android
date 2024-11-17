package br.notasocial.data.model.Auth

data class CustomerAuthRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
)
