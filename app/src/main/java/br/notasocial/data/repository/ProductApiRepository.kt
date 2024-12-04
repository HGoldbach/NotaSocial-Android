package br.notasocial.data.repository

import android.util.Log
import br.notasocial.data.model.Catalog.CatalogCategory
import br.notasocial.data.model.Catalog.CatalogPriceHistory
import br.notasocial.data.model.Catalog.CatalogProduct
import br.notasocial.data.model.ProductDto
import br.notasocial.data.model.Social.ReviewRequest
import br.notasocial.data.model.Social.SocialFavorite
import br.notasocial.data.service.ProductApiService
import retrofit2.Response

interface ProductApiRepository {
    suspend fun getLowestPrice() : Response<CatalogProduct>
    suspend fun getProductById(id: String) : Response<ProductDto>
    suspend fun favoriteProduct(token: String, id: String) : Response<Unit>
    suspend fun searchProduct(name: String) : Response<CatalogProduct>
    suspend fun getPriceHistory(productId: String) : Response<CatalogPriceHistory>
    suspend fun getCategories() : Response<CatalogCategory>
    suspend fun getFavorites(token: String, id: String) : Response<SocialFavorite>
    suspend fun reviewProduct(token: String, productId: String,  reviewRequest: ReviewRequest) : Response<Unit>
    suspend fun getProductsDetails(token: String, productIds: List<String>) : Response<CatalogProduct>
    suspend fun removeFavorite(token: String, productId: String) : Response<Unit>
    suspend fun getProductsByCategory(categoryId: Long) : Response<CatalogProduct>
}

class ProductApiRepositoryImpl(
    private val productApiService: ProductApiService
) : ProductApiRepository {
    override suspend fun getCategories(): Response<CatalogCategory> {
        return productApiService.getCategories(
            page = "0", size = "50", sortDirection = "ASC", sortBy = "id"
        )
    }

    override suspend fun getLowestPrice(): Response<CatalogProduct> {
        return productApiService.getLowestPrice(
            page = "0", size = "6", sortDirection = "ASC", sortBy = "code"
        )
    }

    override suspend fun getProductById(id: String): Response<ProductDto> {
        return productApiService.getProductById(id)
    }

    override suspend fun favoriteProduct(token: String, id: String): Response<Unit> {
        return productApiService.favoriteProduct("Bearer $token", id)
    }

    override suspend fun searchProduct(name: String) : Response<CatalogProduct> {
        return productApiService.searchProduct(page = "0", size = "20", sortDirection = "ASC", sortBy = "code", name = name)
    }

    override suspend fun getPriceHistory(productId: String) : Response<CatalogPriceHistory> {
        return productApiService.getPriceHistory(page = "0", size = "20", sortDirection = "ASC", sortBy = "createdAt", productId = productId)
    }

    override suspend fun getFavorites(token: String, id: String) : Response<SocialFavorite> {
        return productApiService.getFavorites("Bearer $token", id, page = "0", size = "20", sortDirection = "ASC", sortBy = "name")
    }

    override suspend fun reviewProduct(token: String, productId: String, reviewRequest: ReviewRequest) : Response<Unit> {
        return productApiService.reviewProduct("Bearer $token", productId, reviewRequest)
    }

    override suspend fun getProductsDetails(token: String, productIds: List<String>) : Response<CatalogProduct> {
        Log.i("PRODUCTAPIREPO", "getProductsDetails: $productIds")
        return productApiService.getProductsDetails("Bearer $token", productIds, page = "0", size = "20", sortDirection = "ASC", sortBy = "code")
    }

    override suspend fun removeFavorite(token: String, productId: String) : Response<Unit> {
        return productApiService.removeFavorite("Bearer $token", productId)
    }

    override suspend fun getProductsByCategory(categoryId: Long) : Response<CatalogProduct> {
        return productApiService.getProductsByCategory(categoryId, page = "0", size = "20", sortDirection = "ASC", sortBy = "code")
    }
}