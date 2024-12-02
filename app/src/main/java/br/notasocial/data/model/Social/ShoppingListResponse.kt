package br.notasocial.data.model.Social


import com.google.gson.annotations.SerializedName

data class ShoppingListResponse(
    @SerializedName("content")
    val content: List<Content>,
) {
    data class Content(
        @SerializedName("branch")
        val branch: Branch,
        @SerializedName("productQuantity")
        val productQuantity: Int,
        @SerializedName("products")
        val products: List<Product>,
        @SerializedName("totalPrice")
        val totalPrice: Double
    ) {
        data class Branch(
            @SerializedName("correlationId")
            val correlationId: String,
            @SerializedName("distance")
            val distance: Double,
            @SerializedName("id")
            val id: String,
            @SerializedName("store")
            val store: Store
        ) {
            data class Store(
                @SerializedName("address")
                val address: Address,
                @SerializedName("cnpj")
                val cnpj: String,
                @SerializedName("id")
                val id: String,
                @SerializedName("name")
                val name: String
            ) {
                data class Address(
                    @SerializedName("city")
                    val city: String,
                    @SerializedName("neighborhood")
                    val neighborhood: String,
                    @SerializedName("number")
                    val number: String,
                    @SerializedName("state")
                    val state: String,
                    @SerializedName("street")
                    val street: String
                )
            }
        }

        data class Product(
            @SerializedName("price")
            val price: Double,
            @SerializedName("productId")
            val productId: String,
            @SerializedName("quantity")
            val quantity: Int,
            @SerializedName("total")
            val total: Double
        )
    }

    data class Pageable(
        @SerializedName("offset")
        val offset: Int,
        @SerializedName("pageNumber")
        val pageNumber: Int,
        @SerializedName("pageSize")
        val pageSize: Int,
        @SerializedName("paged")
        val paged: Boolean,
        @SerializedName("sort")
        val sort: Sort,
        @SerializedName("unpaged")
        val unpaged: Boolean
    ) {
        data class Sort(
            @SerializedName("empty")
            val empty: Boolean,
            @SerializedName("sorted")
            val sorted: Boolean,
            @SerializedName("unsorted")
            val unsorted: Boolean
        )
    }

    data class Sort(
        @SerializedName("empty")
        val empty: Boolean,
        @SerializedName("sorted")
        val sorted: Boolean,
        @SerializedName("unsorted")
        val unsorted: Boolean
    )
}