package br.notasocial.ui.viewmodel.consumidor.userprofile

import androidx.lifecycle.ViewModel
import br.notasocial.data.model.Social.Notification
import br.notasocial.data.model.Social.NotificationType

class NotificationsViewModel() : ViewModel() {

    val notifications = listOf(
        Notification(
            id = 1,
            type = NotificationType.ALTERACAO_PRECO,
            title = "Alteração de Preço do Produto",
            description = "O produto pao forma seven boys foi atualizado para um novo valor",
            link = "",
            date = "05 Maio 2024 - 12:30"
        ),
        Notification(
            id = 2,
            type = NotificationType.SEGUIDOR,
            title = "Novo Seguidor",
            description = "O usuário Maria José acabou de seguir você no Nota Social",
            link = "",
            date = "04 Maio 2024 - 10:15"
        ),
        Notification(
            id = 3,
            type = NotificationType.COMENTARIO,
            title = "Novo Comentário",
            description = "Você recebeu um comentário na sua avaliação do produto pao forma seven boys",
            link = "",
            date = "03 Maio 2024 - 15:45"
        )
    )
}