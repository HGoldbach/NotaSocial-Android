package br.notasocial.data.model.User

import com.google.gson.annotations.SerializedName

data class StoreBranch(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("store")
    val branch: Branch? = null,
    @SerializedName("distance")
    val distance: Double? = null,
)
