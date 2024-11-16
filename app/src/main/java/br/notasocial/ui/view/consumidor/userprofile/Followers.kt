package br.notasocial.ui.view.consumidor.userprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.ui.NotaSocialBottomAppBar
import br.notasocial.ui.NotaSocialTopAppBar
import br.notasocial.ui.components.profile.FollowUserItem
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.view.consumidor.buscarproduto.FilterSearch
import br.notasocial.ui.view.consumidor.buscarproduto.SearchBar

object FollowersDestination : NavigationDestination {
    override val route = "userprofile_followers"
    override val title = "Seguidores"
}

@Composable
fun FollowersScreen(
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
    navigateToPerfil: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                .padding(it)
                .fillMaxSize()
                .background(color = Color.hsl(0f, 0f, .97f, 1f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp)
            ) {
                FollowersTopSection()
                Spacer(modifier = Modifier.padding(10.dp))
                FollowesGrid(
                    navigateToProfile = navigateToPerfil
                )
            }
        }
    }
}

@Composable
fun FollowersTopSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.followers_title),
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            fontFamily = ralewayFamily,
            modifier = Modifier.padding(vertical = 15.dp)
        )
        Row {
            SearchBar(
                placeholderText = stringResource(id = R.string.followers_input_placeholder),
                searchText = "",
                onSearchChange = {},
                searchProduct = {},
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            FilterSearch()
        }
    }
}

@Composable
fun FollowesGrid(
    navigateToProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(3),
    ) {
        items(12) {
            FollowUserItem(
                navigateToProfile = navigateToProfile,
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FollowersScreenPreview() {
    NotasocialTheme {
        FollowersScreen(
            {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FollowersTopSectionPreview() {
    NotasocialTheme {
        FollowersTopSection()
    }
}

@Preview(showBackground = true)
@Composable
fun SeguidoresGridPreview() {
    NotasocialTheme {
        FollowesGrid(
            navigateToProfile = {}
        )
    }
}