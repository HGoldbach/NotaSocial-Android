package br.notasocial.data.model


import com.google.gson.annotations.SerializedName

data class Catalog(
    @SerializedName("content")
    val content: List<Content?>?,
    @SerializedName("empty")
    val empty: Boolean?,
    @SerializedName("first")
    val first: Boolean?,
    @SerializedName("last")
    val last: Boolean?,
    @SerializedName("number")
    val number: Int?,
    @SerializedName("numberOfElements")
    val numberOfElements: Int?,
    @SerializedName("pageable")
    val pageable: Pageable?,
    @SerializedName("size")
    val size: Int?,
    @SerializedName("sort")
    val sort: Sort?,
    @SerializedName("totalElements")
    val totalElements: Int?,
    @SerializedName("totalPages")
    val totalPages: Int?
) {
    data class Content(
        @SerializedName("code")
        val code: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("price")
        val price: Double?,
        @SerializedName("storeCorrelationId")
        val storeCorrelationId: String?
    )

    data class Pageable(
        @SerializedName("offset")
        val offset: Int?,
        @SerializedName("pageNumber")
        val pageNumber: Int?,
        @SerializedName("pageSize")
        val pageSize: Int?,
        @SerializedName("paged")
        val paged: Boolean?,
        @SerializedName("sort")
        val sort: Sort?,
        @SerializedName("unpaged")
        val unpaged: Boolean?
    ) {
        data class Sort(
            @SerializedName("empty")
            val empty: Boolean?,
            @SerializedName("sorted")
            val sorted: Boolean?,
            @SerializedName("unsorted")
            val unsorted: Boolean?
        )
    }

    data class Sort(
        @SerializedName("empty")
        val empty: Boolean?,
        @SerializedName("sorted")
        val sorted: Boolean?,
        @SerializedName("unsorted")
        val unsorted: Boolean?
    )
}