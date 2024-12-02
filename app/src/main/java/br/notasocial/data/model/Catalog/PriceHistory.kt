package br.notasocial.data.model.Catalog

import com.google.gson.annotations.SerializedName

data class PriceHistory(
    /*
    @SerializedName("storeId")
    val storeId: String?,
    @SerializedName("branchId")
    val branchId: String?,
    @SerializedName("storeName")
    val storeName: String?,

     */
    @SerializedName("price")
    val price: Double?,
    @SerializedName("priceChangeDate")
    val priceChangeDate: String?
)

/*
"storeId": "2e69df8d-1214-4a16-9b74-5c6d197f16b8",
"branchId": "23fe4b7f-b106-4fee-88c4-af42997b4627",
"storeName": "SUPERMERCADO MATRIZ CURITIBA LTDA - EPP",
"neighborhood": null,
"price": 2.99,
"priceChangeDate": "2024-11-17T19:38:04"

 */