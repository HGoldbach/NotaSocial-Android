package br.notasocial.data.service

import br.notasocial.data.model.Catalog.CatalogProduct
import br.notasocial.data.model.Receipt.ReceiptsResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ReceiptApiService {
    @Headers("Content-Type: application/json")
    @POST("receipt-scan/scan")
    suspend fun getReceiptInformation(
        @Header("Authorization") token: String,
        @Body requestBody: RequestBody
    ): Response<Void>

    @GET("catalog/product")
    suspend fun getCatalog(
        @Query("page") page: String,
        @Query("size") size: String,
        @Query("sortDirection") sortDirection: String,
        @Query("sortBy") sortBy: String
    ) : Response<CatalogProduct>

    @GET("receipt-scan/scan/receipts/{keycloakId}")
    suspend fun getReceipts(
        @Header("Authorization") token: String,
        @Path("keycloakId") keycloakId: String,
        @Query("page") page: String,
        @Query("size") size: String,
        @Query("sortDirection") sortDirection: String,
        @Query("sortBy") sortBy: String
    ) : Response<ReceiptsResponse>
}