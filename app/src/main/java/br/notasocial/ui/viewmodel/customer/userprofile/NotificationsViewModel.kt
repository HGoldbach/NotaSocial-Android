package br.notasocial.ui.viewmodel.customer.userprofile

import androidx.lifecycle.ViewModel
import br.notasocial.data.model.Social.Notification
import br.notasocial.data.model.Social.NotificationType

class NotificationsViewModel() : ViewModel() {

    val notifications = listOf(
        Notification(
            id = 1,
            type = NotificationType.SEGUIDOR,
            title = "Novo Seguidor",
            description = "O usuário Willian Wrynn acabou de seguir você no Nota Social",
            link = "",
            date = "30 Novembro 2024 - 09:15"
        ),
        Notification(
            id = 2,
            type = NotificationType.SEGUIDOR,
            title = "Novo Seguidor",
            description = "O usuário Fernanda Wrynn acabou de seguir você no Nota Social",
            link = "",
            date = "30 Novembro 2024 - 10:15"
        ),
    )
}