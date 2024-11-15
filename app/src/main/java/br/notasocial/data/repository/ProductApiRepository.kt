package br.notasocial.data.repository

import br.notasocial.data.model.Category
import br.notasocial.data.service.ProductApiService
import retrofit2.Response

interface ProductApiRepository {
    suspend fun getCategories() : Response<List<Category>>
}

class ProductApiRepositoryImpl(
    private val productApiService: ProductApiService
) : ProductApiRepository {
    override suspend fun getCategories(): Response<List<Category>> {
        return productApiService.getCategories()
    }
}