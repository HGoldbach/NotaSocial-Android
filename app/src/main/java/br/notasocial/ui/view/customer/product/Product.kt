package br.notasocial.ui.view.customer.product

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.data.model.Catalog.PriceHistory
import br.notasocial.data.model.Catalog.Product
import br.notasocial.data.model.ProductDto
import br.notasocial.data.model.Social.Review
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.components.chart.LineChart
import br.notasocial.ui.components.product.ProductInfo
import br.notasocial.ui.components.product.ProductReview
import br.notasocial.ui.components.product.ProductReviewDialog
import br.notasocial.ui.components.product.SimilarProduct
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.viewmodel.customer.product.ProductUiState
import br.notasocial.ui.viewmodel.customer.product.ProductViewModel

object ProductDestination : NavigationDestination {
    override val route = "product"
    override val title = "Produto"
    const val produtoIdArg = "produtoId"
    val routeWithArgs = "$route/{$produtoIdArg}"
}

@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    navigateToProduct: (String) -> Unit,
    navigateToProfile: (String) -> Unit,
    viewModel: ProductViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState
    val context = LocalContext.current
    val reviewUiState = viewModel.reviewUiState

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ProductViewModel.UiEvent.FavoriteSuccess -> {
                    Toast.makeText(context, "Produto favoritado com sucesso!", Toast.LENGTH_SHORT)
                        .show()
                }

                is ProductViewModel.UiEvent.AddToListSuccess -> {
                    Toast.makeText(
                        context,
                        "Produto adicionado à lista com sucesso!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                is ProductViewModel.UiEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .background(color = Color.hsl(0f, 0f, 0.97f, 1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            when (uiState) {
                is ProductUiState.Success -> ProductSuccess(
                    product = uiState.product,
                    onAddToCart = viewModel::addProductToUser,
                    onReviewTextChange = viewModel::updateReviewText,
                    onRatingChange = viewModel::updateRating,
                    reviewText = reviewUiState.reviewText,
                    reviewRating = reviewUiState.selectedRating,
                    priceHistory = viewModel.priceHistory,
                    onFavoriteProduct = { viewModel.favoriteProduct(uiState.product.id) },
                    onSaveReview = { viewModel.submitReview() },
                    reviews = viewModel.reviews,
                    userRole = viewModel.userRole,
                    similarProducts = viewModel.relatedProducts,
                    navigateToProfile = navigateToProfile,
                    navigateToProduct = navigateToProduct,
                    branchStoreName = viewModel.storeBranch.branch?.name,
                )

                is ProductUiState.Error -> Text(text = uiState.message)
                is ProductUiState.Loading -> Text(text = "LOADING")
            }
        }
    }
}

@Composable
fun ProductSuccess(
    onAddToCart: (String) -> Unit,
    product: ProductDto,
    priceHistory: List<PriceHistory>,
    onFavoriteProduct: () -> Unit,
    modifier: Modifier = Modifier,
    onReviewTextChange: (String) -> Unit,
    onRatingChange: (String) -> Unit,
    reviewText: String,
    reviewRating: String,
    onSaveReview: () -> Unit,
    reviews: List<Review>,
    navigateToProfile: (String) -> Unit,
    userRole: String,
    similarProducts: List<Product>,
    navigateToProduct: (String) -> Unit,
    branchStoreName: String?
) {
    ProductInfo(
        reviewsTotal = reviews.size,
        reviewRating = reviews.map { it.rating }.average(),
        product = product,
        branchStoreName = branchStoreName,
    )
    ProductActions(
        onAddToCart = onAddToCart,
        onReviewTextChange = onReviewTextChange,
        onRatingChange = onRatingChange,
        reviewText = reviewText,
        reviewRating = reviewRating,
        product = product,
        onFavoriteProduct = onFavoriteProduct,
        onSaveReview = onSaveReview,
        userRole = userRole,
        modifier = Modifier.padding(vertical = 20.dp)
    )
    Divider(
        modifier = Modifier
            .height(3.dp)
            .fillMaxWidth(),
        color = Color.White
    )
    SimilarProducts(
        products = similarProducts,
        navigateToProduct = navigateToProduct,
        modifier = Modifier.padding(vertical = 20.dp)
    )
    Divider(
        modifier = Modifier
            .height(3.dp)
            .fillMaxWidth(),
        color = Color.White
    )
    PriceHistory(
        priceHistory = priceHistory,
        modifier = Modifier.padding(vertical = 20.dp)
    )
    Divider(
        modifier = Modifier
            .height(3.dp)
            .fillMaxWidth(),
        color = Color.White
    )
    ProductReviews(
        reviews = reviews,
        modifier = Modifier.padding(vertical = 20.dp),
        navigateToProfile = navigateToProfile
    )
}

@Composable
fun ProductActions(
    product: ProductDto,
    onFavoriteProduct: () -> Unit,
    modifier: Modifier = Modifier,
    onRatingChange: (String) -> Unit,
    onReviewTextChange: (String) -> Unit,
    onSaveReview: () -> Unit,
    reviewText: String,
    reviewRating: String,
    onAddToCart: (String) -> Unit,
    userRole: String
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = modifier
    ) {
        TextButton(
            onClick = { onAddToCart(product.id) },
            modifier = Modifier
                .weight(1f)
                .height(35.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.textButtonColors(
                containerColor = Color.hsl(123f, 0.66f, 0.33f, 1f)
            )
        ) {
            Text(
                text = "Adicionar a Lista",
                fontFamily = ralewayFamily,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
        }
        TextButton(
            onClick = onFavoriteProduct,
            modifier = Modifier
                .weight(1f)
                .height(35.dp)
                .padding(horizontal = 5.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.textButtonColors(
                containerColor = Color.hsl(123f, 0.66f, 0.33f, 1f)
            )
        ) {
            Text(
                text = "Favoritar",
                fontFamily = ralewayFamily,
                fontSize = 10.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
        TextButton(
            onClick = { showDialog = !showDialog },
            modifier = Modifier
                .weight(1f)
                .height(35.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.textButtonColors(
                containerColor = Color.hsl(123f, 0.66f, 0.33f, 1f)
            )
        ) {
            Text(
                text = "Avaliar",
                fontFamily = ralewayFamily,
                fontSize = 10.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
        if (showDialog && userRole == "CUSTOMER") {
            ProductReviewDialog(
                onRatingChange = onRatingChange,
                onReviewTextChange = onReviewTextChange,
                reviewText = reviewText,
                reviewRating = reviewRating,
                onSaveReview = onSaveReview,
                product = product,
                onDismissRequest = {
                    showDialog = !showDialog
                },
            )
        }
    }
}

@Composable
fun SimilarProducts(
    products: List<Product>,
    modifier: Modifier = Modifier,
    navigateToProduct: (String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Produtos Similares",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = Color.Black,
            fontFamily = ralewayFamily,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        LazyRow() {
            items(items = products, key = { it.id!! }) { product ->
                SimilarProduct(
                    product = product,
                    navigateToProduct = navigateToProduct,
                    modifier = Modifier.padding(end = 10.dp)
                )
            }
        }
    }
}

@Composable
fun PriceHistory(
    priceHistory: List<PriceHistory>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Histórico de Preço",
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            fontFamily = ralewayFamily,
        )
        LineChart(
            priceHistory = priceHistory
        )
    }
}

@Composable
fun ProductReviews(
    reviews: List<Review>,
    modifier: Modifier = Modifier,
    navigateToProfile: (String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Avaliações",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = Color.Black,
            fontFamily = ralewayFamily
        )
        if (reviews.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Nenhuma avaliação realizada",
                    fontWeight = FontWeight.Medium,
                    fontFamily = ralewayFamily,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        } else {
            reviews.forEach { review ->
                ProductReview(
                    review = review,
                    modifier = Modifier.padding(vertical = 20.dp),
                    navigateToProfile = navigateToProfile
                )
                Divider(
                    thickness = 5.dp,
                    color = Color.White,
                    modifier = Modifier.width(100.dp)
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun ProductScreenPreview() {
    NotasocialTheme {
        ProductScreen(
            navigateToProfile = {},
            navigateToProduct = {}
        )
    }
}