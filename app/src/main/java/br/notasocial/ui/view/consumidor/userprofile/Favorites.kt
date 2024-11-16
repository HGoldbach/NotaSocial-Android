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
import androidx.compose.foundation.lazy.grid.items
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
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.data.model.Social.Product
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.NotaSocialBottomAppBar
import br.notasocial.ui.NotaSocialTopAppBar
import br.notasocial.ui.components.product.FavoriteItem
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.view.consumidor.buscarproduto.FilterSearch
import br.notasocial.ui.view.consumidor.buscarproduto.SearchBar
import br.notasocial.ui.viewmodel.consumidor.userprofile.FavoritesViewModel

object FavoritesDestination : NavigationDestination {
    override val route = "userprofile_favorites"
    override val title = "Favoritos"
}

@Composable
fun FavoritesScreen(
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
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = viewModel(factory = AppViewModelProvider.Factory)
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
                modifier = Modifier.fillMaxSize().padding(horizontal = 15.dp)
            ) {
                FavoritesTopSection()
                Spacer(modifier = Modifier.padding(10.dp))
                FavoritesGrid(
                    favorites = viewModel.favorites
                )
            }
        }
    }
}

@Composable
fun FavoritesTopSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.favorites_title),
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            fontFamily = ralewayFamily,
            modifier = Modifier.padding(bottom = 15.dp)
        )
        Row(
            modifier = Modifier
        ) {
            SearchBar(
                placeholderText = stringResource(id = R.string.search_product_placeholder),
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
fun FavoritesGrid(
    favorites: List<Product>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
    ) {
        items(items = favorites, key = { it.id }) { product ->
            FavoriteItem(
                product = product,
                modifier = Modifier
                    .padding(5.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    NotasocialTheme {
        FavoritesScreen(
            {}, {}, {}, {}, {}, {}, {}, {}, {}, {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesTopSectionPreview() {
    NotasocialTheme {
        FavoritesTopSection()
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesGridPreview() {
    NotasocialTheme {
        val favorites = listOf(
            Product(
                1, "Pao Forma Seven Boys", R.drawable.pao_forma, true, 6.69
            ),
            Product(
                2, "Mamão Formosa", R.drawable.mamao_formosa, false, 12.50
            ),
            Product(
                3, "Arroz TP1 Buriti", R.drawable.arroz_buriti, false, 6.69
            )
        )
        FavoritesGrid(
            favorites = favorites
        )
    }
}
