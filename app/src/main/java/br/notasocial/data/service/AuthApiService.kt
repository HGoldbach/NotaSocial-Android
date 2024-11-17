package br.notasocial.data.service

import br.notasocial.data.model.Auth.CustomerAuthRequest
import br.notasocial.data.model.Auth.CustomerAuthResponse
import br.notasocial.data.model.Auth.StoreAuthRequest
import br.notasocial.data.model.Auth.StoreAuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApiService {

    @Headers("Content-Type: application/json")
    @POST("user/store")
    suspend fun registerStore(@Body store: StoreAuthRequest): Response<StoreAuthResponse>

    @Headers("Content-Type: application/json")
    @POST("user/account/user")
    suspend fun registerCustomer(@Body customer: CustomerAuthRequest): Response<CustomerAuthResponse>
}