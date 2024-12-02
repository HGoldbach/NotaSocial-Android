package br.notasocial.data.model

import com.google.gson.annotations.SerializedName

data class Store(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("keycloakId")
    val keycloakId: String? = null,
    @SerializedName("approved")
    val approved: Boolean? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("cnpj")
    val cnpj: String? = null,
    @SerializedName("urlPhoto")
    val image: String? = null,
)
