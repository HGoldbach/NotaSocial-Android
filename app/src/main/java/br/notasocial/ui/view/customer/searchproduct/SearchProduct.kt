package br.notasocial.ui.view.consumidor.searchproduct

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.data.model.Catalog.CatalogProduct
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.NotaSocialBottomAppBar
import br.notasocial.ui.NotaSocialTopAppBar
import br.notasocial.ui.components.product.SearchProductItem
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.viewmodel.buscarproduto.SearchProductViewModel
import br.notasocial.ui.viewmodel.buscarproduto.CatalogUiState
import kotlinx.coroutines.launch

object SearchProductDestination : NavigationDestination {
    override val route = "search_product"
    override val title = "Buscar Produto"
}

@Composable
fun SearchProductScreen(
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
    navigateToProduct: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchProductViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val searchText by viewModel.searchText.collectAsState()
    val product by viewModel.product.collectAsState()
    val coroutineScope = rememberCoroutineScope()
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
                .fillMaxHeight()
                .padding(it)
                .background(Color.hsl(0f, 0f, .97f, 1f))
        ) {
            SearchProductTopSection(
                searchText = searchText,
                onSearchChange = { text ->
                    coroutineScope.launch {
                        viewModel.onSearchTextChange(text)
                    }
                },
                searchProduct = { viewModel.searchProduct(searchText) }
            )
            when (val uiState: CatalogUiState = viewModel.catalogUiState) {
                is CatalogUiState.Loading -> ProductLoading()
                is CatalogUiState.Error -> ProductError()
                is CatalogUiState.Success -> ProductGrid(
                    products = uiState.catalogProduct,
                    navigateToProduct = navigateToProduct
                )
            }
        }
    }
}

@Composable
fun SearchProductTopSection(
    searchText: String,
    onSearchChange: (String) -> Unit,
    searchProduct: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        SearchBar(
            searchText = searchText,
            onSearchChange = onSearchChange ,
            searchProduct = searchProduct,
            placeholderText = stringResource(id = R.string.search_product_placeholder),
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        FilterSearch()
    }
}

@Composable
fun ProductGrid(
    products: CatalogProduct,
    navigateToProduct: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if(products.products!!.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Nenhum produto encontrado")
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier.padding(horizontal = 10.dp)
        ) {
            items(items = products.products, key = { it?.id!! }) { product ->
                if (product != null) {
                    SearchProductItem(
                        product = product,
                        navigateToProduct = navigateToProduct,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {}
                    )
                }
            }
        }
    }
}

@Composable
fun ProductLoading(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.hsl(0f, 0f, .97f, 1f)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.loading_img),
            contentDescription = "",
            modifier = Modifier.size(192.dp)
        )
    }
}

@Composable
fun ProductError(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.hsl(0f, 0f, .97f, 1f)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Erro ao buscar os produtos")
    }
}

@Composable
fun SearchBar(
    placeholderText: String,
    searchText: String,
    onSearchChange: (String) -> Unit,
    searchProduct: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = searchText,
        onValueChange = {
            onSearchChange(it)
            searchProduct()
        },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.search_regular),
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )
        },
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
        ),
        placeholder = {
            Text(
                text = placeholderText,
                fontFamily = ralewayFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        },
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        modifier = modifier
            .heightIn(min = 50.dp),
        shape = RoundedCornerShape(10.dp)
    )
}

@Composable
fun FilterSearch(
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = { /*TODO*/ },
        modifier = modifier
            .size(50.dp)
            .background(Color.White, shape = CircleShape)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.filter_solid),
            contentDescription = "",
            modifier = Modifier.size(18.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchProductScreenPreview() {
    NotasocialTheme {
        SearchProductScreen(
            {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchProductTopSectionPreview() {
    NotasocialTheme {
        SearchProductTopSection(
            searchText = "",
            onSearchChange = {},
            searchProduct = {}
        )
    }
}

@Preview
@Composable
fun FilterSearchPreview() {
    NotasocialTheme {
        FilterSearch()
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    NotasocialTheme {
        SearchBar("", "", {}, {})
    }
}

