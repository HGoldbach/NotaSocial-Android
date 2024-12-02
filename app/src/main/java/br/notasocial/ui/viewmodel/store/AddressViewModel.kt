package br.notasocial.ui.viewmodel.store

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.data.model.StoreDb.AddressDb
import br.notasocial.data.model.StoreDb.PromotionDb
import br.notasocial.data.repository.StoreDbRepository
import br.notasocial.data.repository.UserPreferencesRepository
import kotlinx.coroutines.launch

class AddressViewModel(
    private val storeDbRepository: StoreDbRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private var keycloakId: String by mutableStateOf("")
    var uiState: AddressUiState by mutableStateOf(AddressUiState())
        private set

    init {
        viewModelScope.launch {
            userPreferencesRepository.currentUserData.collect { userData ->
                if (userData.keycloakId.isNotEmpty()) {
                    keycloakId = userData.keycloakId
                    loadAddressesByStoreId(keycloakId)
                }
            }

        }
    }

    private fun loadAddressesByStoreId(storeId: String) {
        viewModelScope.launch {
            try {
                val addresses = storeDbRepository.getAddressesByStoreId(storeId)
                uiState = uiState.copy(addresses = addresses)
            } catch (e: Exception) {
                // Tratar erro, como falha ao acessar o banco de dados
                Log.e("PromotionViewModel", "Error loading promotions by storeId", e)
            }
        }
    }

    fun removeAddress(addressId: Int) {
        viewModelScope.launch {
            try {
                storeDbRepository.removeAddress(addressId)
            } catch (e: Exception) {
                Log.e("AddressViewModel", "Error removing address", e)
            }
        }
    }

    fun saveAddress() {

        val addressData = AddressDb(
            storeId = keycloakId,
            street = uiState.street,
            number = uiState.number,
            complement = uiState.complement,
            district = uiState.neighborhood,
            city = uiState.city,
            state = uiState.state,
            zipCode = uiState.cep,
            phone = uiState.telephone
        )

        viewModelScope.launch {
            try {
                storeDbRepository.insertAddress(addressData)
                // Caso tenha sucesso, pode atualizar o estado ou mostrar uma mensagem de sucesso
                // Exemplo: exibir uma mensagem de sucesso ou navegar para outra tela
            } catch (e: Exception) {
                Log.e("AddressViewModel", "Error saving promotion", e)
                // Tratar erro, exibir mensagem de falha
                // Exemplo: exibir uma mensagem de erro
            }
        }
    }

    fun onStreetChange(street: String) {
        uiState = uiState.copy(street = street)
    }

    fun onNumberChange(number: String) {
        uiState = uiState.copy(number = number)
    }

    fun onComplementChange(complement: String) {
        uiState = uiState.copy(complement = complement)
    }

    fun onNeighborhoodChange(neighborhood: String) {
        uiState = uiState.copy(neighborhood = neighborhood)
    }

    fun onCityChange(city: String) {
        uiState = uiState.copy(city = city)
    }

    fun onStateChange(state: String) {
        uiState = uiState.copy(state = state)
    }

    fun onCepChange(cep: String) {
        uiState = uiState.copy(
            cep = cep,
            isCepValid = Validator.isValidCep(cep)
        )
    }

    fun onTelephoneChange(telephone: String) {
        uiState = uiState.copy(telephone = telephone)
    }
}

object Validator {
    fun isValidCep(cep: String) = cep.length == 8 && cep.all { it.isDigit() }
    fun isTelephoneValid(telephone: String) = Patterns.PHONE.matcher(telephone).matches()
}