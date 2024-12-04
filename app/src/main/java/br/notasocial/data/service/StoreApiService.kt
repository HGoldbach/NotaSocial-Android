package br.notasocial.data.service

import br.notasocial.data.model.Store
import br.notasocial.data.model.User.StoreBranch
import br.notasocial.data.model.User.StoreResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StoreApiService {

    @GET("register/account/store")
    suspend fun getStores(
        @Query("page") page: String,
        @Query("size") size: String,
        @Query("sortDirection") sortDirection: String,
        @Query("sortBy") sortBy: String
    ): Response<StoreResponse>

    @GET("register/store/branch/{storeId}")
    suspend fun getBranchStore(
        @Path("storeId") storeId: String
    ) : Response<StoreBranch>

    @GET("register/account/store/{storeId}")
    suspend fun getStoreById(
        @Path("storeId") storeId: String
    ) : Response<Store>

    @GET("register/account/store/search")
    suspend fun searchStore(
        @Query("page") page: String,
        @Query("size") size: String,
        @Query("sortDirection") sortDirection: String,
        @Query("sortBy") sortBy: String,
        @Query("firstname") firstname: String
    ) : Response<StoreResponse>
}