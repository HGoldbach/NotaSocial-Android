package br.notasocial.ui.view.customer.searchstore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import br.notasocial.ui.components.store.StoreItem
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.view.customer.searchproduct.SearchBar
import br.notasocial.ui.view.customer.searchproduct.SearchProductTopSection
import br.notasocial.ui.viewmodel.customer.searchstore.SearchStoreViewModel
import br.notasocial.ui.viewmodel.customer.searchstore.StoreUiState
import kotlinx.coroutines.launch

object SearchStoreDestination : NavigationDestination {
    override val route = "search_store"
    override val title = "Buscar Estabelecimento"
}

@Composable
fun SearchStoreScreen(
    navigateToStore: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchStoreViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val searchText by viewModel.searchText.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier
            .background(color = Color.hsl(0f, 0f, 0.97f, 1f))
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            SearchBar(
                placeholderText = stringResource(id = R.string.search_store_placeholder),
                searchText = searchText,
                onSearchChange = { text ->
                    coroutineScope.launch {
                        viewModel.onSearchTextChange(text)
                    }
                },
                searchProduct = { viewModel.searchStore(searchText) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
            )
            when (val uiState: StoreUiState = viewModel.storeUiState) {
                is StoreUiState.Loading -> LoadingStores()
                is StoreUiState.Error -> ErrorStores(errorMessage = uiState.errorMessage)
                is StoreUiState.Success -> SuccessStoresGrid(
                    store = uiState.stores,
                    navigateToStore = navigateToStore
                )
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
    navigateToStore: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
    ) {
        items(items = store, key = { it.id!! }) { store ->
            StoreItem(
                store,
                navigateToStore,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchStoreScreenPreview() {
    NotasocialTheme {
        SearchStoreScreen(
            {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StoreGridPreview() {
    NotasocialTheme {
        val mockStores = listOf(
            Store("1", keycloakId = "1", approved = false, name = "Carrefour", email = "william.henry.moody@my-own-personal-domain.com", cnpj = "123456789", image = ""),
            Store("2", keycloakId = "1", approved = false, name = "Carrefour", email = "william.henry.moody@my-own-personal-domain.com", cnpj = "123456789", image = ""),
        )
        SuccessStoresGrid(
            store = mockStores,
            navigateToStore = {}
        )
    }
}
