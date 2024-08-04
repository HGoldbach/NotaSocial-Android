package br.notasocial.data

import android.content.Context
import br.notasocial.data.repository.NetworkNotaSocialRepository
import br.notasocial.data.repository.NotaSocialApiRepository
import br.notasocial.data.service.ReceiptApiService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val notaSocialApiRepository: NotaSocialApiRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.18.11:8080/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    override val notaSocialApiRepository: NotaSocialApiRepository by lazy {
        NetworkNotaSocialRepository(retrofitService)
    }

    private val retrofitService: ReceiptApiService by lazy {
        retrofit.create(ReceiptApiService::class.java)
    }
}