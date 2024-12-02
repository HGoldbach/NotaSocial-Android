package br.notasocial.ui.view.customer.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.components.category.CategoryItem
import br.notasocial.ui.components.product.ProductItem
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.viewmodel.customer.home.CategoryHomeUiState
import br.notasocial.ui.viewmodel.customer.home.HomeUiState
import br.notasocial.ui.viewmodel.customer.home.HomeViewModel

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val title = "Home"
}

@Composable
fun HomeScreen(
    navigateToProduct: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val scrollState = rememberScrollState()
    val uiState = viewModel.homeUiState
    val categoryUiState = viewModel.categoryHomeUiState
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is HomeViewModel.UiEvent.AddToListSuccess -> {
                    Toast.makeText(context, "Produto adicionado com sucesso!", Toast.LENGTH_SHORT)
                        .show()
                }
                is HomeViewModel.UiEvent.FavoriteSuccess -> {
                    Toast.makeText(context, "Produto favoritado com sucesso!", Toast.LENGTH_SHORT)
                        .show()
                }
                is HomeViewModel.UiEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.hsl(0f, 0f, .97f, 1f))
            .verticalScroll(scrollState)
    ) {
        NotaSocialTitle()
        Spacer(modifier = Modifier.height(25.dp))
        SlideSection()
        Spacer(modifier = Modifier.height(25.dp))
        CategorySection(
            uiState = categoryUiState,
        )
        Spacer(modifier = Modifier.height(25.dp))
        LastUpdatesSection(
            uiState = uiState,
            onFavorite = viewModel::favoriteProduct,
            onAddToCart = viewModel::addProductToUser,
            userRole = viewModel.userRole,
            navigateToProduct = navigateToProduct
        )
    }
}

@Composable
fun NotaSocialTitle(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.app_name).uppercase(),
            color = Color.hsl(128f, .52f, .47f, 1f),
            fontFamily = ralewayFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp
        )
        Text(
            text = stringResource(id = R.string.app_slogan),
            color = Color.Black,
            fontFamily = ralewayFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        )
    }
}

@Composable
fun SlideSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)
        ) {
            Text(
                text = "Encontre\nbaratos.",
                color = Color.hsl(128f, .52f, .47f, 1f),
                fontFamily = ralewayFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
            Text(
                text = " onde estão os produtos mais",
                color = Color.Black,
                fontFamily = ralewayFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
        }
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.circle_solid),
                    contentDescription = "",
                    tint = Color.hsl(123f, .63f, .33f, 1f),
                    modifier = modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    painter = painterResource(id = R.drawable.circle_solid),
                    contentDescription = "",
                    tint = Color.hsl(0f, 0f, .85f, 1f),
                    modifier = modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    painter = painterResource(id = R.drawable.circle_solid),
                    contentDescription = "",
                    tint = Color.hsl(0f, 0f, .85f, 1f),
                    modifier = modifier.size(14.dp)
                )
            }
            Text(
                text = stringResource(id = R.string.search_product),
                color = Color.hsl(128f, .52f, .47f, 1f),
                textDecoration = TextDecoration.Underline,
                fontFamily = ralewayFamily,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun CategorySection(
    uiState: CategoryHomeUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(start = 20.dp, end = 10.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.categories),
                color = Color.Black,
                fontFamily = ralewayFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.right_arrow),
                    contentDescription = "",
                    tint = Color.Black,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
    when (uiState) {
        is CategoryHomeUiState.Error -> Text(text = uiState.errorMessage)
        is CategoryHomeUiState.Loading ->
            Column(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.loading_img),
                    contentDescription = "",
                    modifier = Modifier.size(100.dp)
                )
            }
        is CategoryHomeUiState.Success ->
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 5.dp)
            ) {
                items(items = uiState.categories, key = { it.id }) { category ->
                    CategoryItem(
                        categoryImage = category.image,
                        text = category.name
                    )
                }
            }
    }

}

@Composable
fun LastUpdatesSection(
    uiState: HomeUiState,
    onFavorite: (String) -> Unit,
    onAddToCart: (String) -> Unit,
    modifier: Modifier = Modifier,
    navigateToProduct: (String) -> Unit,
    userRole: String
) {
    Column(
        modifier = modifier
            .padding(start = 20.dp, end = 10.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.products_last_updates),
                color = Color.Black,
                fontFamily = ralewayFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.right_arrow),
                    contentDescription = "",
                    tint = Color.Black,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
    when (uiState) {
        is HomeUiState.Error -> Text(text = uiState.errorMessage)
        is HomeUiState.Loading ->
            Column(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.loading_img),
                    contentDescription = "",
                    modifier = Modifier.size(100.dp)
                )
            }
        is HomeUiState.Success ->
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(items = uiState.catalogProduct.products, key = { it.id!! }) { product ->
                    ProductItem(
                        product =  product,
                        navigateToProductDetail = { navigateToProduct(product.id!!) },
                        onFavorite = onFavorite,
                        onAddToCart = onAddToCart,
                        userRole = userRole,
                        modifier = Modifier
                            .padding(8.dp)
                            .background(Color.White, shape = RoundedCornerShape(15.dp))
                            .padding(10.dp)
                    )
                }
            }
    }

}

@Preview(showBackground = true)
@Composable
fun NotaSocialTitlePreview() {
    NotasocialTheme {
        NotaSocialTitle()
    }
}

@Preview(showBackground = true)
@Composable
fun SlideSectionPreview() {
    NotasocialTheme {
        SlideSection()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NotasocialTheme {
        HomeScreen(
            {}
        )
    }
}