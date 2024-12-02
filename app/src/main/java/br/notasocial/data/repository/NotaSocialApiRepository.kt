package br.notasocial.data.repository

import br.notasocial.data.model.Catalog.CatalogProduct
import br.notasocial.data.model.Receipt.ReceiptsResponse
import br.notasocial.data.service.ReceiptApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Response

interface NotaSocialApiRepository {
    suspend fun getReceiptInformation(token: String, url: String): Response<Void>
    suspend fun getCatalog() : Response<CatalogProduct>
    suspend fun getReceipts(token: String, keycloakId: String) : Response<ReceiptsResponse>
}

class NetworkNotaSocialRepository(
    private val receiptApiService: ReceiptApiService
) : NotaSocialApiRepository {
    override suspend fun getReceiptInformation(token: String, url: String): Response<Void> {
        val requestBody =
            RequestBody.create("application/json".toMediaTypeOrNull(), """{"url": "$url"}""")
        return receiptApiService.getReceiptInformation("Bearer $token", requestBody)
    }

    override suspend fun getCatalog() : Response<CatalogProduct> {
        return receiptApiService.getCatalog(page = "0", size = "10", sortDirection = "ASC", sortBy = "name")
    }

    override suspend fun getReceipts(token: String, keycloakId: String) : Response<ReceiptsResponse> {
        return receiptApiService.getReceipts("bearer $token", keycloakId, "0", "10", "ASC", "scannedAt")
    }

}