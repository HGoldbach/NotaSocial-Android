package br.notasocial.data.model.User

import br.notasocial.data.model.Store
import com.google.gson.annotations.SerializedName

data class StoreResponse(
    @SerializedName("content")
    val stores: List<Store>
)
