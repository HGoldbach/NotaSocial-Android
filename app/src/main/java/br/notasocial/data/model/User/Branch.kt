package br.notasocial.data.model.User

import br.notasocial.data.model.Address
import com.google.gson.annotations.SerializedName

data class Branch(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("address")
    val address: Address,
    @SerializedName("cnpj")
    val cnpj: String,
)
