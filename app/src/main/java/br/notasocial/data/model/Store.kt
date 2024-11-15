package br.notasocial.data.model

import com.google.gson.annotations.SerializedName

data class Store(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nome")
    val name: String,
    @SerializedName("endereco")
    val address: String,
    @SerializedName("telefone")
    val phone: String,
)
