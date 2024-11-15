package br.notasocial.data.service

import br.notasocial.data.model.Store
import retrofit2.Response
import retrofit2.http.GET

interface StoreApiService {

    @GET("estabelecimentos")
    suspend fun getStores() : Response<List<Store>>
}