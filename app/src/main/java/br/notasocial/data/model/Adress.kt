package br.notasocial.data.model

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("city")
    val city: String?,
    @SerializedName("neighborhood")
    val neighborhood: String?,
    @SerializedName("number")
    val number: String?,
    @SerializedName("state")
    val state: String?,
    @SerializedName("street")
    val street: String?
)
