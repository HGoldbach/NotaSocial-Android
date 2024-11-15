package br.notasocial.data.model

import com.google.gson.annotations.SerializedName

data class Receipt(
    @SerializedName("accessKey")
    val accessKey: String?,
    @SerializedName("generalInformation")
    val generalInformation: GeneralInformation?,
    @SerializedName("items")
    val items: List<Item?>?,
    @SerializedName("paymentMethod")
    val paymentMethod: String?,
    @SerializedName("store")
    val store: Store,
    @SerializedName("tax")
    val tax: Double?,
    @SerializedName("totalItems")
    val totalItems: Int?,
    @SerializedName("totalValue")
    val totalValue: Double?,
    @SerializedName("valuePaid")
    val valuePaid: Double?
) {
    data class GeneralInformation(
        @SerializedName("authorizationProtocol")
        val authorizationProtocol: AuthorizationProtocol?,
        @SerializedName("issuance")
        val issuance: Issuance?,
        @SerializedName("number")
        val number: String?,
        @SerializedName("series")
        val series: String?
    ) {
        data class AuthorizationProtocol(
            @SerializedName("code")
            val code: String?,
            @SerializedName("date")
            val date: List<Int?>?
        )

        data class Issuance(
            @SerializedName("date")
            val date: List<Int?>?,
            @SerializedName("issuer")
            val issuer: String?
        )
    }

    data class Item(
        @SerializedName("amount")
        val amount: Double?,
        @SerializedName("code")
        val code: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("totalValue")
        val totalValue: Double?,
        @SerializedName("unit")
        val unit: String?,
        @SerializedName("unitValue")
        val unitValue: Double?
    )
}

