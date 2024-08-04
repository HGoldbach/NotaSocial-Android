package br.notasocial

import android.app.Application
import br.notasocial.data.AppContainer
import br.notasocial.data.DefaultAppContainer

class NotaSocialApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}