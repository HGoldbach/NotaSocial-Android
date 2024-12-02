package br.notasocial.data.service

import br.notasocial.data.model.Social.Ranking
import br.notasocial.data.model.Social.Review
import br.notasocial.data.model.Social.ShoppingListItem
import br.notasocial.data.model.Social.ShoppingListRequest
import br.notasocial.data.model.Social.ShoppingListResponse
import br.notasocial.data.model.User.UserResponse
import br.notasocial.data.model.User.UserSocial
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiService {

    @GET("social/profile/{keycloakId}")
    suspend fun getUserProfile(@Header("Authorization") token: String, @Path("keycloakId") id: String): Response<UserResponse>

    @GET("social/product/reviews/{productId}")
    suspend fun getReviews(
        @Header("Authorization") token: String,
        @Path("productId") id: String,
        @Query("page") page: String,
        @Query("size") size: String,
        @Query("sortDirection") sortDirection: String,
        @Query("sortBy") sortBy: String,
    ): Response<List<Review>>

    @GET("social/profile/following")
    suspend fun getFollowing(
        @Header("Authorization") token: String,
        @Query("page") page: String,
        @Query("size") size: String,
        @Query("sortDirection") sortDirection: String,
        @Query("sortBy") sortBy: String,
    ): Response<UserSocial>

    @GET("social/profile/{keycloakId}/following")
    suspend fun getUserFollowing(
        @Header("Authorization") token: String,
        @Path("keycloakId") id: String,
        @Query("page") page: String,
        @Query("size") size: String,
        @Query("sortDirection") sortDirection: String,
        @Query("sortBy") sortBy: String,
    ): Response<UserSocial>

    @GET("social/profile/followers")
    suspend fun getFollowers(
        @Header("Authorization") token: String,
        @Query("page") page: String,
        @Query("size") size: String,
        @Query("sortDirection") sortDirection: String,
        @Query("sortBy") sortBy: String,
    ): Response<UserSocial>

    @GET("social/profile/{keycloakId}/followers")
    suspend fun getUserFollowers(
        @Header("Authorization") token: String,
        @Path("keycloakId") id: String,
        @Query("page") page: String,
        @Query("size") size: String,
        @Query("sortDirection") sortDirection: String,
        @Query("sortBy") sortBy: String,
    ): Response<UserSocial>

    @GET("social/profile/{keycloakId}/reviews")
    suspend fun getUserReviews(
        @Header("Authorization") token: String,
        @Path("keycloakId") id: String,
        @Query("page") page: String,
        @Query("size") size: String,
        @Query("sortDirection") sortDirection: String,
        @Query("sortBy") sortBy: String,
    ) : Response<List<Review>>


    @POST("social/profile/follow/{keycloakId}")
    suspend fun followUser(
        @Header("Authorization") token: String,
        @Path("keycloakId") id: String
    ) : Response<Unit>

    @POST("social/shopping-list/add-products")
    suspend fun addProductsToShoppingList(
        @Header("Authorization") token: String,
        @Body products: ShoppingListRequest
    ) : Response<Unit>

    @POST("social/shopping-list/remove-product")
    suspend fun removeProductFromShoppingList(
        @Header("Authorization") token: String,
        @Body products: ShoppingListRequest
    ) : Response<Unit>


    @GET("social/shopping-list/calculate")
    suspend fun calculateDistance(
        @Header("Authorization") token: String,
        @Query("cep") cep: String,
        @Query("distance") distance: String = "100"
    ) : Response<ShoppingListResponse>

    @GET("social/ranking")
    suspend fun getRanking(
        @Query("page") page: String,
        @Query("size") size: String,
        @Query("sortDirection") sortDirection: String,
        @Query("sortBy") sortBy: String,
    ) : Response<Ranking>
}