package br.notasocial.ui.view.customer.searchcategory

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.data.model.Catalog.CatalogProduct
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.components.product.SearchProductItem
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.viewmodel.customer.searchcategory.CatalogUiState
import br.notasocial.ui.viewmodel.customer.searchcategory.SearchCategoryViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest

object SearchCategoryDestination : NavigationDestination {
    override val route = "search_category"
    override val title = "Buscar Category"
    const val categoryIdArg = "categoryId"
    const val categoryNameArg = "categoryName"
    const val categoryImageArg = "categoryImage"
    val routeWithArgs = "${route}/{$categoryIdArg}/{$categoryNameArg}/{$categoryImageArg}"
}

@Composable
fun SearchCategoryScreen(
    navigateToProduct: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchCategoryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is SearchCategoryViewModel.UiEvent.AddToListSuccess -> {
                    Toast.makeText(context, "Produto adicionado com sucesso!", Toast.LENGTH_SHORT)
                        .show()
                }
                is SearchCategoryViewModel.UiEvent.FavoriteSuccess -> {
                    Toast.makeText(context, "Produto favoritado com sucesso!", Toast.LENGTH_SHORT)
                        .show()
                }
                is SearchCategoryViewModel.UiEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .fillMaxHeight()
            .background(Color.hsl(0f, 0f, .97f, 1f))
    ) {
        when (val uiState: CatalogUiState = viewModel.catalogUiState) {
            is CatalogUiState.Loading -> ProductLoading()
            is CatalogUiState.Error -> ProductError()
            is CatalogUiState.Success -> CategoryGrid(
                onAddToCart = viewModel::addProductToUser,
                onFavorite = viewModel::favoriteProduct,
                userRole = viewModel.userRole,
                products = uiState.catalogProduct,
                categoryImage = viewModel.categoryImage,
                categoryName = viewModel.categoryName,
                navigateToProduct = navigateToProduct
            )
        }
    }
}

@Composable
fun CategoryGrid(
    onAddToCart: (String) -> Unit,
    onFavorite: (String) -> Unit,
    products: CatalogProduct,
    navigateToProduct: (String) -> Unit,
    modifier: Modifier = Modifier,
    userRole: String,
    categoryImage: String,
    categoryName: String,
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val image = "https://i.imgur.com/${categoryImage}"
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(120.dp)
        )
        Text(
            text = categoryName.uppercase(),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontFamily = ralewayFamily,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Produtos",
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 15.dp).fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontFamily = ralewayFamily,
        )
    }
    if (products.products.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Nenhum produto encontrado", color = Color.Black)
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            items(items = products.products, key = { it.id!! }) { product ->
                SearchProductItem(
                    onAddToCart = onAddToCart,
                    onFavorite = onFavorite,
                    userRole = userRole,
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

