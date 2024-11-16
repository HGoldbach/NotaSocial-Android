package br.notasocial.ui.view.consumidor.userprofile

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
import br.notasocial.R
import br.notasocial.ui.NotaSocialBottomAppBar
import br.notasocial.ui.NotaSocialTopAppBar
import br.notasocial.ui.components.profile.MenuItem
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily

object UserProfileHomeDestination : NavigationDestination {
    override val route = "userprofile_home"
    override val title = "Perfil"
}

@Composable
fun HomeScreen(
    navigateToBuscarProduto: () -> Unit,
    navigateToEstabelecimentos: () -> Unit,
    navigateToRanking: () -> Unit,
    navigateToFavoritos: () -> Unit,
    navigateToShoplist: () -> Unit,
    navigateToCadastrarNota: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToRegistrar: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToPerfilProprio: () -> Unit,
    navigateToNotas: () -> Unit,
    navigateToSeguindo: () -> Unit,
    navigateToSeguidores: () -> Unit,
    navigateToNotificacoes: () -> Unit,
    navigateToConta: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            NotaSocialTopAppBar(
                navigateToBuscarProduto = navigateToBuscarProduto,
                navigateToEstabelecimentos = navigateToEstabelecimentos,
                navigateToRanking = navigateToRanking,
                navigateToFavoritos = navigateToFavoritos,
                navigateToShoplist = navigateToShoplist,
                navigateToCadastrarNota = navigateToCadastrarNota,
                navigateToLogin = navigateToLogin,
                navigateToRegistrar = navigateToRegistrar,
                navigateToHome = navigateToHome
            )
        },
        bottomBar = {
            NotaSocialBottomAppBar(
                navigateToHome = navigateToHome,
                navigateToBuscarProduto = navigateToBuscarProduto,
                navigateToPerfilProprio = navigateToPerfilProprio
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
                .background(Color.hsl(0f, 0f, .97f, 1f))
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                HomeTopSection()
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
}

@Composable
fun HomeTopSection(
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
            text = "Jose maria".uppercase(),
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
        HomeScreen(
            {},{},{},{},{},{},{},{},{},{},{},{},{},{},{}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilHomeTopSectionPreview() {
    NotasocialTheme {
        HomeTopSection()
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilHomeMenuSectionPreview() {
    NotasocialTheme {
        HomeMenuSection(
            {},{},{},{},{},{}
        )
    }
}
