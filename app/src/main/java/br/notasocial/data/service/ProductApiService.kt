package br.notasocial.data.service

import br.notasocial.data.model.Category
import retrofit2.Response
import retrofit2.http.GET

interface ProductApiService {

    @GET("categorias")
    suspend fun getCategories(): Response<List<Category>>

}