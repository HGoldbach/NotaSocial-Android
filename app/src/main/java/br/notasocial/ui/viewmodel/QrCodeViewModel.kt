package br.notasocial.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import br.notasocial.data.repository.NotaSocialApiRepository



class QrCodeViewModel(
    private val notaSocialRepository : NotaSocialApiRepository
) : ViewModel() {

    var receiptUrl by mutableStateOf("")
        private set

    fun setReceiptUrlId(url: String) {
        val delimiter = "="
        receiptUrl = url.substringAfter(delimiter, missingDelimiterValue = null.toString())
        Log.d("QRCODEVIEWMODEL", receiptUrl)
    }





//    fun getReceiptInformation() {
//        viewModelScope.launch {
//            receiptUiState = ReceiptUiState.Loading
//            receiptUiState = try {
//                val response = notaSocialRepository.getReceiptInformation("http://www.fazenda.pr.gov.br/nfce/qrcode?p=41240576189406003907651180003239511000825586|2|1|1|51398FCFAE7ABF9A887E0214303678FD315A423B")
//                if (response.isSuccessful) {
//                    ReceiptUiState.Success("SUCESSO") // ou algum estado que represente sucesso sem dados
//                } else {
//                    ReceiptUiState.Error("Response not successful: ${response.code()}")
//                }
//            } catch (e: IOException) {
//                ReceiptUiState.Error(e.message ?: "Network Error")
//            } catch (e: HttpException) {
//                ReceiptUiState.Error(e.message ?: "HTTP Error")
//            }
//            Log.d("RECEIPT", receiptUiState.toString())
//        }
//    }





}