package br.notasocial.data.repository

import br.notasocial.data.model.Catalog
import br.notasocial.data.model.Receipt
import br.notasocial.data.service.ReceiptApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import kotlin.coroutines.resumeWithException

interface NotaSocialApiRepository {
    suspend fun getReceiptInformation(url: String): Response<Void>
    suspend fun getCatalog() : Response<Catalog>
}

class NetworkNotaSocialRepository(
    private val receiptApiService: ReceiptApiService
) : NotaSocialApiRepository {
    override suspend fun getReceiptInformation(url: String): Response<Void> {
        val requestBody =
            RequestBody.create("application/json".toMediaTypeOrNull(), """{"url": "$url"}""")
        return receiptApiService.getReceiptInformation(requestBody)
    }

    override suspend fun getCatalog() : Response<Catalog> {
        return receiptApiService.getCatalog(page = "0", size = "10", sortDirection = "ASC", sortBy = "name")
    }
}