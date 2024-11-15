package br.notasocial.data.repository

import br.notasocial.data.model.Receipt.Receipt
import br.notasocial.data.model.Store
import br.notasocial.data.service.StoreApiService
import retrofit2.Response

interface StoreApiRepository {
    suspend fun getStores() : Response<List<Store>>
    suspend fun getStoryById() : String
}

class StoreApiRepositoryImpl(
    private val storeApiService: StoreApiService
) : StoreApiRepository {
    override suspend fun getStores() : Response<List<Store>> {
        return storeApiService.getStores()
    }

    override suspend fun getStoryById(): String {
        return ""
    }
}