package br.notasocial.ui.view.consumidor.searchstore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.data.model.Store
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.NotaSocialBottomAppBar
import br.notasocial.ui.NotaSocialTopAppBar
import br.notasocial.ui.components.store.StoreItem
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.view.consumidor.searchproduct.SearchBar
import br.notasocial.ui.viewmodel.consumidor.searchstore.SearchStoreViewModel
import br.notasocial.ui.viewmodel.consumidor.searchstore.StoreUiState

object SearchStoreDestination : NavigationDestination {
    override val route = "search_store"
    override val title = "Buscar Estabelecimento"

}

@Composable
fun SearchStoreScreen(
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
    navigateToEstabelecimento: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchStoreViewModel = viewModel(factory = AppViewModelProvider.Factory)
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
            modifier = modifier.padding(it)
            .background(color = Color.hsl(0f, 0f, 0.97f, 1f))
            .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SearchBar(
                    placeholderText = stringResource(id = R.string.search_store_placeholder),
                    searchText = "",
                    onSearchChange = {},
                    searchProduct = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                )
                when(val uiState: StoreUiState = viewModel.storeUiState) {
                    is StoreUiState.Loading -> LoadingStores()
                    is StoreUiState.Error -> ErrorStores(errorMessage = uiState.errorMessage)
                    is StoreUiState.Success -> SuccessStoresGrid(
                        store = uiState.stores,
                        navigateToStore = navigateToEstabelecimento
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingStores(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Loading"
        )
    }
}

@Composable
fun ErrorStores(
    errorMessage: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = errorMessage
        )
    }
}

@Composable
fun SuccessStoresGrid(
    store: List<Store>,
    navigateToStore: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
    ) {
        items(items = store, key = { it.id }) { store ->
            StoreItem(
                store,
                navigateToStore,
                modifier = Modifier.padding(15.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchStoreScreenPreview() {
    NotasocialTheme {
        SearchStoreScreen(
            {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StoreGridPreview() {
    NotasocialTheme {
        val mockStores = listOf(
            Store(1,"Carrefour","Address 01", "(11) 1234-5678"),
            Store(2,"Carrefour","Address 02", "(11) 1234-5678"),
            Store(3,"Carrefour","Address 03", "(11) 1234-5678"),
            Store(4,"Carrefour","Address 04", "(11) 1234-5678"),
        )
        SuccessStoresGrid(
            store = mockStores,
            navigateToStore = {}
        )
    }
}
