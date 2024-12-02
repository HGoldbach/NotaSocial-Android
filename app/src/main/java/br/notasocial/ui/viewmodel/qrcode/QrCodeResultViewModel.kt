package br.notasocial.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.data.repository.NotaSocialApiRepository
import br.notasocial.data.repository.UserPreferencesRepository
import br.notasocial.ui.view.customer.qrcode.QrCodeResultDestination
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface ReceiptUiState {
    data object Success: ReceiptUiState
    data class Error(val errorMessage: String): ReceiptUiState
    data object Loading: ReceiptUiState
}

class QrCodeResultViewModel(
    savedStateHandle: SavedStateHandle,
    private val notaSocialRepository: NotaSocialApiRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val receiptId: String = checkNotNull(savedStateHandle[QrCodeResultDestination.receiptIdArg])
    private var token: String = ""
    private val baseUrl = "http://www.fazenda.pr.gov.br/nfce/qrcode?p="

    var receiptUrl by mutableStateOf("")
        private set

    fun setReceiptUrlId(url: String) {
        val delimiter = "="
        receiptUrl = url.substringAfter(delimiter, missingDelimiterValue = null.toString())
        Log.d("QRCODEVIEWMODEL", receiptUrl)
    }

    var receiptUiState: ReceiptUiState by mutableStateOf(ReceiptUiState.Loading)
        private set

    init {
        viewModelScope.launch {
            userPreferencesRepository.currentUserData.collect { userData ->
                if (userData.token.isNotEmpty()) {
                    token = userData.token
                    registerReceipt(token)
                }
            }
        }
    }

    private fun registerReceipt(token: String) {
        viewModelScope.launch {
            receiptUiState = ReceiptUiState.Loading
            receiptUiState = try {
                val result = notaSocialRepository.getReceiptInformation(token = token, "$baseUrl$receiptId")
                if (result.isSuccessful) {
                    ReceiptUiState.Success
                } else {
                    Log.e("QrCodeResultViewModel", "Error: $result")
                    ReceiptUiState.Error("Error: ${result.code()}")
                }
            } catch (e: IOException) {
                ReceiptUiState.Error(e.message!!)
            } catch (e: HttpException) {
                ReceiptUiState.Error(e.message!!)
            }
        }
    }
}