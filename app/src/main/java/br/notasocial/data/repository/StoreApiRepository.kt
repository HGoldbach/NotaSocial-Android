package br.notasocial.data.repository

import br.notasocial.data.model.Receipt.Receipt
import br.notasocial.data.model.Store
import br.notasocial.data.model.User.StoreBranch
import br.notasocial.data.model.User.StoreResponse
import br.notasocial.data.service.StoreApiService
import retrofit2.Response

interface StoreApiRepository {
    suspend fun getStores() : Response<StoreResponse>
    suspend fun getBranchStore(storeId: String) : Response<StoreBranch>
    suspend fun getStoreById(storeId: String) : Response<Store>
}

class StoreApiRepositoryImpl(
    private val storeApiService: StoreApiService
) : StoreApiRepository {
    override suspend fun getStores() : Response<StoreResponse> {
        return storeApiService.getStores(
            page = "0", size = "20", sortDirection = "ASC", sortBy = "name"
        )
    }

    override suspend fun getBranchStore(storeId: String) : Response<StoreBranch> {
        return storeApiService.getBranchStore(storeId)
    }

    override suspend fun getStoreById(storeId: String) : Response<Store> {
        return storeApiService.getStoreById(storeId)
    }
}