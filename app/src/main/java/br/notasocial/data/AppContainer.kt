package br.notasocial.data

import android.content.Context
import br.notasocial.data.database.StoreDatabase
import br.notasocial.data.repository.AuthApiRepository
import br.notasocial.data.repository.AuthApiRepositoryImpl
import br.notasocial.data.repository.NetworkNotaSocialRepository
import br.notasocial.data.repository.NotaSocialApiRepository
import br.notasocial.data.repository.OfflineStoreDbRepository
import br.notasocial.data.repository.ProductApiRepository
import br.notasocial.data.repository.ProductApiRepositoryImpl
import br.notasocial.data.repository.StoreApiRepository
import br.notasocial.data.repository.StoreApiRepositoryImpl
import br.notasocial.data.repository.StoreDbRepository
import br.notasocial.data.repository.UserApiRepository
import br.notasocial.data.repository.UserApiRepositoryImpl
import br.notasocial.data.service.AuthApiService
import br.notasocial.data.service.ProductApiService
import br.notasocial.data.service.ReceiptApiService
import br.notasocial.data.service.StoreApiService
import br.notasocial.data.service.UserApiService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val notaSocialApiRepository: NotaSocialApiRepository
    val storeApiRepository: StoreApiRepository
    val productApiRepository: ProductApiRepository
    val authApiRepository: AuthApiRepository
    val userApiRepository: UserApiRepository
    val storeDbRepository: StoreDbRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://7929-2001-1284-f502-aa4f-d8d1-93b9-d6cf-351d.ngrok-free.app/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    override val notaSocialApiRepository: NotaSocialApiRepository by lazy {
        NetworkNotaSocialRepository(retrofitReceiptService)
    }

    override val storeApiRepository: StoreApiRepository by lazy {
        StoreApiRepositoryImpl(retrofitStoreService)
    }

    override val productApiRepository: ProductApiRepository by lazy {
        ProductApiRepositoryImpl(retrofitProductService)
    }

    override val authApiRepository: AuthApiRepository by lazy {
        AuthApiRepositoryImpl(retrofitAuthService)
    }

    override val userApiRepository: UserApiRepository by lazy {
        UserApiRepositoryImpl(retrofitUserService)
    }

    override val storeDbRepository: StoreDbRepository by lazy {
        OfflineStoreDbRepository(
            StoreDatabase.getDatabase(context).storeDao(),
            StoreDatabase.getDatabase(context).promotionDao(),
            StoreDatabase.getDatabase(context).addressDao()
        )
    }

    private val retrofitReceiptService: ReceiptApiService by lazy {
        retrofit.create(ReceiptApiService::class.java)
    }

    private val retrofitStoreService: StoreApiService by lazy {
        retrofit.create(StoreApiService::class.java)
    }

    private val retrofitProductService: ProductApiService by lazy {
        retrofit.create(ProductApiService::class.java)
    }

    private val retrofitAuthService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }

    private val retrofitUserService: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }



}
