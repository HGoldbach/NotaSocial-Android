package br.notasocial.ui.viewmodel.customer.contact

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.ui.utils.sendEmailToGmail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactViewModel() : ViewModel() {

    private val _emailStatus = MutableStateFlow("")
    val emailStatus: StateFlow<String> get() = _emailStatus

    fun sendEmail(recipient: String, subject: String, body: String) {
        viewModelScope.launch {
            _emailStatus.value = "Enviando email..."
            try {
                withContext(Dispatchers.IO) {
                    sendEmailToGmail(recipient, subject, body)
                }
                _emailStatus.value = "E-mail enviado com sucesso!"
            } catch (e: Exception) {
                _emailStatus.value = "Erro ao enviar e-mail: ${e.message}"
                Log.e("ContactViewModel", "Erro ao enviar o e-mail", e)
            }
        }
    }
}