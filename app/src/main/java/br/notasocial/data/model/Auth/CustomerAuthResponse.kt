package br.notasocial.data.model.Auth

data class CustomerAuthResponse(
    val id: String,
    val keycloakId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val urlPhoto: String? = null,
)
