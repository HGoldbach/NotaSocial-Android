package br.notasocial.data.repository

import br.notasocial.data.model.Auth.CustomerAuthRequest
import br.notasocial.data.model.Auth.CustomerAuthResponse
import br.notasocial.data.model.Auth.SignInAuthRequest
import br.notasocial.data.model.Auth.SignInAuthResponse
import br.notasocial.data.model.Auth.StoreAuthRequest
import br.notasocial.data.model.Auth.StoreAuthResponse
import br.notasocial.data.service.AuthApiService
import retrofit2.Response

interface AuthApiRepository {
    suspend fun registerStore(store: StoreAuthRequest): Response<StoreAuthResponse>
    suspend fun registerCustomer(customer: CustomerAuthRequest): Response<CustomerAuthResponse>
    suspend fun signIn(signIn: SignInAuthRequest): Response<SignInAuthResponse>
    suspend fun updateCustomer(token: String, customer: CustomerAuthRequest): Response<Unit>
}

class AuthApiRepositoryImpl(
    private val authApiService: AuthApiService
) : AuthApiRepository {

    override suspend fun registerStore(store: StoreAuthRequest): Response<StoreAuthResponse> {
        return authApiService.registerStore(store)
    }

    override suspend fun registerCustomer(customer: CustomerAuthRequest): Response<CustomerAuthResponse> {
        return authApiService.registerCustomer(customer)
    }

    override suspend fun signIn(signIn: SignInAuthRequest): Response<SignInAuthResponse> {
        return authApiService.signIn(signIn)
    }

    override suspend fun updateCustomer(token: String, customer: CustomerAuthRequest): Response<Unit> {
        return authApiService.updateCustomer("Bearer $token" ,customer)
    }
}