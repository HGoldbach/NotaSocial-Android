package br.notasocial.data.model.User

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("keycloakId")
    val keycloakId: String = "",
    @SerializedName("firstName")
    val firstName: String = "",
    @SerializedName("lastName")
    val lastName: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("photo")
    val photo: String = "",
)
