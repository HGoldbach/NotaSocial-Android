package br.notasocial.data.repository

import br.notasocial.data.model.Social.Ranking
import br.notasocial.data.model.Social.Review
import br.notasocial.data.model.Social.ShoppingListItem
import br.notasocial.data.model.Social.ShoppingListRequest
import br.notasocial.data.model.Social.ShoppingListResponse
import br.notasocial.data.model.User.UserResponse
import br.notasocial.data.model.User.UserSocial
import br.notasocial.data.service.UserApiService
import retrofit2.Response

interface UserApiRepository {
    suspend fun getUserProfile(token: String, id: String): Response<UserResponse>
    suspend fun getReviews(token: String, id: String): Response<List<Review>>
    suspend fun getFollowing(token: String): Response<UserSocial>
    suspend fun getUserFollowing(token: String, id: String): Response<UserSocial>
    suspend fun getFollowers(token: String): Response<UserSocial>
    suspend fun getUserFollowers(token: String, id: String): Response<UserSocial>
    suspend fun getUserReviews(token: String, id: String): Response<List<Review>>
    suspend fun followUser(token: String, id: String): Response<Unit>
    suspend fun addProductsToShoppingList(token: String, products: ShoppingListRequest): Response<Unit>
    suspend fun calculateDistance(token: String, cep: String): Response<ShoppingListResponse>
    suspend fun removeProductFromShoppingList(token: String, products: ShoppingListRequest): Response<Unit>
    suspend fun getRanking(): Response<Ranking>
}

class UserApiRepositoryImpl(
    private val userApiService: UserApiService
) : UserApiRepository {
    override suspend fun getUserProfile(token: String, id: String): Response<UserResponse> {
        return userApiService.getUserProfile("Bearer $token", id)
    }

    override suspend fun getReviews(token: String, id: String): Response<List<Review>> {
        return userApiService.getReviews("Bearer $token", id, page = "0", size = "20", sortDirection = "DESC", sortBy = "createdAt")
    }

    override suspend fun getFollowing(token: String): Response<UserSocial> {
        return userApiService.getFollowing("Bearer $token", page = "0", size = "20", sortDirection = "DESC", sortBy = "name")
    }

    override suspend fun getUserFollowing(token: String, id: String): Response<UserSocial> {
        return userApiService.getUserFollowing("Bearer $token", id, page = "0", size = "20", sortDirection = "DESC", sortBy = "name")
    }

    override suspend fun getFollowers(token: String): Response<UserSocial> {
        return userApiService.getFollowers("Bearer $token", page = "0", size = "20", sortDirection = "DESC", sortBy = "name")
    }

    override suspend fun getUserFollowers(token: String, id: String): Response<UserSocial> {
        return userApiService.getUserFollowers("Bearer $token", id, page = "0", size = "20", sortDirection = "DESC", sortBy = "name")
    }

    override suspend fun getUserReviews(token: String, id: String): Response<List<Review>> {
        return userApiService.getUserReviews("Bearer $token", id, page = "0", size = "20", sortDirection = "DESC", sortBy = "createdAt")
    }

    override suspend fun followUser(token: String, id: String): Response<Unit> {
        return userApiService.followUser("Bearer $token", id)
    }

    override suspend fun addProductsToShoppingList(token: String, products: ShoppingListRequest): Response<Unit> {
        return userApiService.addProductsToShoppingList("Bearer $token", products)
    }

    override suspend fun removeProductFromShoppingList(token: String, products: ShoppingListRequest): Response<Unit> {
        return userApiService.removeProductFromShoppingList("Bearer $token", products)
    }

    override suspend fun calculateDistance(token: String, cep: String): Response<ShoppingListResponse> {
        return userApiService.calculateDistance("Bearer $token", cep)
    }

    override suspend fun getRanking(): Response<Ranking> {
        return userApiService.getRanking(page = "0", size = "20", sortDirection = "DESC", sortBy = "totalReceipts")
    }
}