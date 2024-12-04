package br.notasocial.ui.utils
import java.util.Properties
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.mail.*



suspend fun sendEmailToGmail(recipient: String, subject: String, body: String) {
    val username = "notasocialapp@gmail.com"
    val password = "kmosfzftkxeosnyx"

    val props = Properties().apply {
        put("mail.smtp.auth", "true")
        put("mail.smtp.starttls.enable", "true")
        put("mail.smtp.host", "smtp.gmail.com")
        put("mail.smtp.port", "587")
    }

    val session = Session.getInstance(props, object : Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication(username, password)
        }
    })

    try {
        val message = MimeMessage(session).apply {
            setFrom(InternetAddress(recipient))
            setRecipients(Message.RecipientType.TO, InternetAddress.parse(username))
            this.subject = subject
            setText(body)
        }

        withContext(Dispatchers.IO) {
            Transport.send(message)
        }
        Log.d("Email", "E-mail enviado com sucesso para $recipient!")
    } catch (e: Exception) {
        Log.e("Email", "Erro ao enviar e-mail: ${e.message}")
    }
}