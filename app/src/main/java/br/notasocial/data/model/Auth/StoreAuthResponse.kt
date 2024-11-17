package br.notasocial.data.model.Auth

data class StoreAuthResponse(
    val id: String,
    val keyCloakId: String,
    val name: String,
    val email: String,
    val cnpj: String,
    val urlPhoto: String? = null,
)
