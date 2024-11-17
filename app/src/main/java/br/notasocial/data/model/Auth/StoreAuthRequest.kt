package br.notasocial.data.model.Auth

data class StoreAuthRequest(
    val name: String,
    val cnpj: String,
    val email: String,
    val password: String,
    val urlPhoto: String? = null
)
