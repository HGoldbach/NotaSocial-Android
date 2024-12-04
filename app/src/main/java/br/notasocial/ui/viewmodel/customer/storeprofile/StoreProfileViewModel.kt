package br.notasocial.ui.viewmodel.customer.storeprofile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.data.model.Store
import br.notasocial.data.model.StoreDb.AddressDb
import br.notasocial.data.model.StoreDb.PromotionDb
import br.notasocial.data.repository.StoreApiRepository
import br.notasocial.data.repository.StoreDbRepository
import br.notasocial.ui.view.customer.product.ProductDestination
import br.notasocial.ui.view.customer.storeprofile.StoreProfileDestination
import kotlinx.coroutines.launch

class StoreProfileViewModel(
    savedStateHandle: SavedStateHandle,
    private val storeApiRepository: StoreApiRepository,
    private val storeDbRepository: StoreDbRepository
) : ViewModel() {

    private val storeId: String = checkNotNull(savedStateHandle[StoreProfileDestination.storeIdArg])

    var isUserFollowing: Boolean by mutableStateOf(false)
        private set




    init {
        getStoreProfile()
    }

    var store: Store by mutableStateOf(Store())
        private set

    var addresses: List<AddressDb> by mutableStateOf(emptyList())
        private set

    var promotions: List<PromotionDb> by mutableStateOf(emptyList())
        private set

    private fun getStoreProfile() {
        viewModelScope.launch {
            try {
                val response = storeApiRepository.getStoreById(storeId)
                if (response.isSuccessful) {
                    store = response.body()!!
                    getAddressesById()
                    getPromotionsById()
                } else {
                    Log.e("StoreProfileViewModel", "Erro ao obter perfil da loja: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("StoreProfileViewModel", "Erro ao obter perfil da loja", e)
            }
        }
    }

    private fun getAddressesById() {
        viewModelScope.launch {
            try {
                Log.d("StoreProfileViewModel", "Obtendo endereços da loja com ID: ${store.keycloakId!!}")
                addresses = storeDbRepository.getAddressesByStoreId(store.keycloakId!!)
                Log.d("StoreProfileViewModel", "Endereços obtidos: $addresses")
            } catch (e: Exception) {
                Log.e("StoreProfileViewModel", "Erro ao obter endereços da loja", e)
            }
        }
    }

    private fun getPromotionsById() {
        viewModelScope.launch {
            try {
                promotions = storeDbRepository.getPromotionsByStoreId(store.keycloakId!!)
            } catch (e: Exception) {
                Log.e("StoreProfileViewModel", "Erro ao obter promoções da loja", e)
            }
        }
    }
}