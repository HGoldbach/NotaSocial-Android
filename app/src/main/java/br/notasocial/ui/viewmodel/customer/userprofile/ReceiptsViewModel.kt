package br.notasocial.ui.viewmodel.customer.userprofile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.data.model.Receipt.Receipt
import br.notasocial.data.repository.NotaSocialApiRepository
import br.notasocial.data.repository.UserApiRepository
import br.notasocial.data.repository.UserPreferencesRepository
import kotlinx.coroutines.launch

class ReceiptsViewModel(
    private val notaSocialRepository: NotaSocialApiRepository,
    private val userApiRepository: UserApiRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private var token: String = ""
    private var keycloakId: String = ""
    var receipts: List<Receipt> by mutableStateOf(listOf())
        private set

    init {
        viewModelScope.launch {
            userPreferencesRepository.currentUserData.collect { userData ->
                if (userData.token.isNotEmpty()) {
                    token = userData.token
                    keycloakId = userData.keycloakId
                    getReceipts(token, keycloakId)
                }
            }
        }
    }

    private fun getReceipts(token: String, keycloakId: String) {
        viewModelScope.launch {
            try {
                val response = notaSocialRepository.getReceipts(token, keycloakId)
                if(response.isSuccessful) {
                    receipts = response.body()!!.content
                } else {
                    Log.e("ReceiptsViewModel", "error getReceipts: ${response.message()}")
                }
            } catch(e : Exception) {
                Log.e("ReceiptsViewModel", "error getReceipts: ${e.message}")
            }
        }
    }

}