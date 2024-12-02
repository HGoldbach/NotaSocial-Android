package br.notasocial.data.model.User

import com.google.gson.annotations.SerializedName

data class UserSocial(
    @SerializedName("content")
    val users: List<UserResponse> = emptyList(),
)
