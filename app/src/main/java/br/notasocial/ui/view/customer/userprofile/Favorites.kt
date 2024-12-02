package br.notasocial.ui.view.customer.userprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.components.product.FavoriteItem
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.view.customer.searchproduct.FilterSearch
import br.notasocial.ui.view.customer.searchproduct.SearchBar
import br.notasocial.ui.viewmodel.customer.userprofile.FavoritesUiState
import br.notasocial.ui.viewmodel.customer.userprofile.FavoritesViewModel

object FavoritesDestination : NavigationDestination {
    override val route = "userprofile_favorites"
    override val title = "Favoritos"
}

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    navigateToProduct: (String) -> Unit,
    viewModel: FavoritesViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val uiState = viewModel.uiState

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.hsl(0f, 0f, .97f, 1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp)
        ) {
            FavoritesTopSection()
            Spacer(modifier = Modifier.padding(10.dp))
            FavoritesGrid(
                uiState = uiState,
                onRemoveFavorite = viewModel::onRemoveFavorite,
                navigateToProduct = navigateToProduct
            )
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
    uiState: FavoritesUiState,
    modifier: Modifier = Modifier,
    navigateToProduct: (String) -> Unit,
    onRemoveFavorite: (String) -> Unit,
) {
    when (uiState) {
        is FavoritesUiState.Error -> {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = uiState.message
                )
            }
        }
        is FavoritesUiState.Loading -> {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.loading_img),
                    contentDescription = "",
                    modifier = Modifier.size(100.dp)
                )
            }
        }

        is FavoritesUiState.Success -> {
            if (uiState.favorites.isEmpty()) {
                Column(
                    modifier = modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Nenhum produto adicionado aos favoritos",
                        fontSize = 14.sp,
                        fontFamily = ralewayFamily,
                        fontWeight = FontWeight.Medium
                    )
                }
            } else {
                LazyVerticalGrid(
                    modifier = modifier,
                    columns = GridCells.Fixed(2),
                ) {
                    items(items = uiState.favorites, key = { it.id }) { favorite ->
                        FavoriteItem(
                            favorite = favorite,
                            onRemoveFavorite = onRemoveFavorite,
                            navigateToProduct = navigateToProduct,
                            modifier = Modifier
                                .padding(5.dp)
                        )
                    }
                }
            }

        }
    }

}


@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    NotasocialTheme {
        FavoritesScreen(
            navigateToProduct = {}
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

