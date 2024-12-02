package br.notasocial.data.service

import br.notasocial.data.model.Catalog.CatalogCategory
import br.notasocial.data.model.Catalog.CatalogPriceHistory
import br.notasocial.data.model.Catalog.CatalogProduct
import br.notasocial.data.model.ProductDto
import br.notasocial.data.model.Social.ReviewRequest
import br.notasocial.data.model.Social.SocialFavorite
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApiService {

    @GET("catalog/category")
    suspend fun getCategories(
        @Query("page") page: String,
        @Query("size") size: String,
        @Query("sortDirection") sortDirection: String,
        @Query("sortBy") sortBy: String
    ): Response<CatalogCategory>

    @GET("catalog/product/lowest-price")
    suspend fun getLowestPrice(
        @Query("page") page: String,
        @Query("size") size: String,
        @Query("sortDirection") sortDirection: String,
        @Query("sortBy") sortBy: String
    ): Response<CatalogProduct>

    @GET("catalog/product/{id}")
    suspend fun getProductById(
        @Path("id") id: String,
        @Query("includeStore") includeStore: Boolean = true
    ): Response<ProductDto>

    @GET("catalog/product/search")
    suspend fun searchProduct(
        @Query("page") page: String,
        @Query("size") size: String,
        @Query("sortDirection") sortDirection: String,
        @Query("sortBy") sortBy: String,
        @Query("name") name: String,
    ): Response<CatalogProduct>

    @GET("catalog/product/price-history")
    suspend fun getPriceHistory(
        @Query("page") page: String,
        @Query("size") size: String,
        @Query("sortDirection") sortDirection: String,
        @Query("sortBy") sortBy: String,
        @Query("id") productId: String,
    ): Response<CatalogPriceHistory>

    @POST("social/product/favorite/{id}")
    suspend fun favoriteProduct(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<Unit>

    @GET("social/profile/{keycloakId}/favorites")
    suspend fun getFavorites(
        @Header("Authorization") token: String,
        @Path("keycloakId") id: String,
        @Query("page") page: String,
        @Query("size") size: String,
        @Query("sortDirection") sortDirection: String,
        @Query("sortBy") sortBy: String,
    ): Response<SocialFavorite>

    @POST("social/product/reviews/{productId}")
    suspend fun reviewProduct(
        @Header("Authorization") token: String,
        @Path("productId") productId: String,
        @Body reviewRequest: ReviewRequest
    ): Response<Unit>

    @POST("catalog/product/products-details")
    suspend fun getProductsDetails(
        @Header("Authorization") token: String,
        @Body productIds: List<String>,
        @Query("page") page: String,
        @Query("size") size: String,
        @Query("sortDirection") sortDirection: String,
        @Query("sortBy") sortBy: String,
    ): Response<CatalogProduct>

    @POST("social/product/favorite/remove/{productId}")
    suspend fun removeFavorite(
        @Header("Authorization") token: String,
        @Path("productId") productId: String,
    ) : Response<Unit>

}