package br.notasocial.ui.view.customer.userprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.data.model.Social.Notification
import br.notasocial.data.model.Social.NotificationType
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.components.profile.NotificationItem
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.viewmodel.customer.userprofile.NotificationsViewModel

object NotificationsDestination : NavigationDestination {
    override val route = "userprofile_notifications"
    override val title = "Notificações"
}

@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier,
    viewModel: NotificationsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.hsl(0f, 0f, .97f, 1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.notifications_title),
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                fontFamily = ralewayFamily,
                modifier = Modifier.padding(15.dp)
            )
            NotificationsGrid(
                notifications = viewModel.notifications
            )
        }
    }
}

@Composable
fun NotificationsGrid(
    notifications: List<Notification>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(items = notifications, key = { it.id }) { notification ->
            NotificationItem(
                notification = notification,
                modifier = Modifier.padding(vertical = 5.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationsGridPreview() {
    NotasocialTheme {
        val mock = listOf(
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
        NotificationsGrid(notifications = mock)
    }
}
