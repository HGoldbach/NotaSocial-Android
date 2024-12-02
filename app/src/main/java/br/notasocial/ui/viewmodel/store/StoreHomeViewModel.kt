package br.notasocial.ui.viewmodel.store

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.data.model.Store
import br.notasocial.data.model.StoreDb.StoreDb
import br.notasocial.data.repository.StoreApiRepository
import br.notasocial.data.repository.StoreDbRepository
import br.notasocial.data.repository.UserPreferencesRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class StoreHomeViewModel(
    private val storeApiRepository: StoreApiRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val storeDbRepository: StoreDbRepository
) : ViewModel() {

    var keycloakId: String by mutableStateOf("")
        private set

    var stores: List<Store> by mutableStateOf(emptyList())
        private set

    var loggedStore: Store by mutableStateOf(Store())
        private set

    init {
        viewModelScope.launch {
            userPreferencesRepository.currentUserData.collect { userData ->
                if (userData.keycloakId.isNotEmpty()) {
                    keycloakId = userData.keycloakId
                    getStores()
                }
            }

        }
    }

    private fun getStores() {
        viewModelScope.launch {
            try {
                val response = storeApiRepository.getStores()
                if (response.isSuccessful) {
                    stores = response.body()!!.stores
                    getStoreByKeycloakId()
                } else {
                    Log.e("StoreHomeViewModel", "Response not successfull ${response.code()}")
                }
            } catch (e: IOException) {
                Log.e("StoreHomeViewModel", e.message!!)
            } catch (e: HttpException) {
                Log.e("StoreHomeViewModel", e.message!!)
            }
        }
    }

    private fun getStoreByKeycloakId() {
        viewModelScope.launch {
            try {
                val foundStore = stores.find {
                    it.keycloakId != null && it.keycloakId == keycloakId
                }

                loggedStore = foundStore ?: Store()
                saveLoggedStoreLocally(loggedStore)
                Log.d("StoreHomeViewModel", "Logged store: ${loggedStore.name}")
            } catch (e: Exception) {
                Log.e("StoreHomeViewModel", "Error finding store by keycloakId", e)
            }
        }
    }

    private suspend fun saveLoggedStoreLocally(store: Store) {
        storeDbRepository.insertStore(store.toStoreDb())
    }
}

fun Store.toStoreDb(): StoreDb {
    return StoreDb(
        id = this.keycloakId!!,          // Certifique-se de que o ID da Store é o mesmo que o esperado no StoreDb
        name = this.name!!,      // Nome da Store
        cnpj = this.cnpj!!      // O campo CNPJ na StoreDb, mapeando o campo correspondente na Store
    )
}