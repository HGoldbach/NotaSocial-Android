package br.notasocial.data.model.Social

enum class NotificationType {
    ALTERACAO_PRECO,
    SEGUIDOR,
    COMENTARIO
}

data class Notification(
    val id: Int,
    val type: NotificationType,
    val title: String,
    val description: String,
    val link: String,
    val date: String
)
