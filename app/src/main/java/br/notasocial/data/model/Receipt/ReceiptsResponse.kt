package br.notasocial.data.model.Receipt

import com.google.gson.annotations.SerializedName

data class ReceiptsResponse(
    @SerializedName("content")
    val content: List<Receipt> = emptyList()
)
