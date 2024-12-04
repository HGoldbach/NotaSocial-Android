package br.notasocial.data.service

import br.notasocial.data.model.Auth.CustomerAuthRequest
import br.notasocial.data.model.Auth.CustomerAuthResponse
import br.notasocial.data.model.Auth.SignInAuthRequest
import br.notasocial.data.model.Auth.SignInAuthResponse
import br.notasocial.data.model.Auth.StoreAuthRequest
import br.notasocial.data.model.Auth.StoreAuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthApiService {

    @Multipart
    @POST("register/account/store")
    suspend fun registerStore(
        @Part("storeAccountRequestDTO") store: StoreAuthRequest
    ): Response<StoreAuthResponse>

    @Multipart
    @POST("register/account/user")
    suspend fun registerCustomer(
        @Part("customerAccountRequestDTO") customer: CustomerAuthRequest
    ): Response<CustomerAuthResponse>

    @Headers("Content-Type: application/json")
    @POST("auth/auth/generate-token")
    suspend fun signIn(@Body signIn: SignInAuthRequest): Response<SignInAuthResponse>

    @Multipart
    @PATCH("register/account/user")
    suspend fun updateCustomer(
        @Header("Authorization") token: String,
        @Part("customerAccountRequestDTO") customer: CustomerAuthRequest
    ) : Response<Unit>

}