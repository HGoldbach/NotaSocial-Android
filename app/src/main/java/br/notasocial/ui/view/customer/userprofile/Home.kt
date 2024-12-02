package br.notasocial.ui.view.customer.userprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.data.model.User.UserResponse
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.NotaSocialBottomAppBar
import br.notasocial.ui.NotaSocialTopAppBar
import br.notasocial.ui.components.profile.MenuItem
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.viewmodel.customer.userprofile.UserProfileViewModel

object UserProfileHomeDestination : NavigationDestination {
    override val route = "userprofile_home"
    override val title = "Perfil"
}

@Composable
fun UserHomeScreen(
    navigateToFavoritos: () -> Unit,
    navigateToNotas: () -> Unit,
    navigateToSeguindo: () -> Unit,
    navigateToSeguidores: () -> Unit,
    navigateToNotificacoes: () -> Unit,
    navigateToConta: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UserProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.hsl(0f, 0f, .97f, 1f))
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            HomeTopSection(
                user = viewModel.user
            )
            HomeMenuSection(
                navigateToFavoritos = navigateToFavoritos,
                navigateToNotas = navigateToNotas,
                navigateToSeguindo = navigateToSeguindo,
                navigateToSeguidores = navigateToSeguidores,
                navigateToNotificacoes = navigateToNotificacoes,
                navigateToConta = navigateToConta,
                modifier = Modifier.padding(top = 32.dp)
            )
        }
    }
}

@Composable
fun HomeTopSection(
    user: UserResponse,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .size(134.dp)
                .background(Color.White, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.user_solid),
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                colorFilter = ColorFilter.tint(Color.hsl(123f, 0.66f, 0.33f, 1f))
            )
            IconButton(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .zIndex(1f)
                    .size(50.dp)
                    .background(Color.hsl(123f, 0.66f, 0.33f, 1f), shape = CircleShape)
                    .border(5.dp, Color.hsl(0f, 0f, 0.97f, 1f), shape = RoundedCornerShape(80.dp))
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.camera_solid),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
            }
        }
        Text(
            text = "${user.firstName} ${user.lastName}".uppercase(),
            fontFamily = ralewayFamily,
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            letterSpacing = TextUnit(2f, TextUnitType.Sp),
            modifier = Modifier.padding(top = 16.dp),
        )
    }
}

@Composable
fun HomeMenuSection(
    navigateToFavoritos: () -> Unit,
    navigateToNotas: () -> Unit,
    navigateToSeguindo: () -> Unit,
    navigateToSeguidores: () -> Unit,
    navigateToNotificacoes: () -> Unit,
    navigateToConta: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        MenuItem(
            text = stringResource(id = R.string.menu_item_favorites),
            navigate = { navigateToFavoritos() },
        )
        Spacer(modifier = Modifier.size(8.dp))
        MenuItem(
            text = stringResource(id = R.string.menu_item_receipts),
            navigate = { navigateToNotas() }
        )
        Spacer(modifier = Modifier.size(8.dp))
        MenuItem(
            text = stringResource(id = R.string.menu_item_following),
            navigate = { navigateToSeguindo() }
        )
        Spacer(modifier = Modifier.size(8.dp))
        MenuItem(
            text = stringResource(id = R.string.menu_item_followers),
            navigate = { navigateToSeguidores() }
        )
        Spacer(modifier = Modifier.size(8.dp))
        MenuItem(
            text = stringResource(id = R.string.menu_item_notifications),
            navigate = { navigateToNotificacoes() }
        )
        Spacer(modifier = Modifier.size(8.dp))
        MenuItem(
            text = stringResource(id = R.string.menu_item_account),
            navigate = { navigateToConta() }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PerfilHomeScreenPreview() {
    NotasocialTheme {
        UserHomeScreen(
            {}, {}, {}, {}, {}, {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilHomeTopSectionPreview() {
    NotasocialTheme {
        val mockUser = UserResponse(
            firstName = "Hiron",
            lastName = "Goldbach"
        )
        HomeTopSection(
            user = mockUser
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilHomeMenuSectionPreview() {
    NotasocialTheme {
        HomeMenuSection(
            {}, {}, {}, {}, {}, {}
        )
    }
}
