package br.notasocial.ui.view.consumidor.shoplist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.data.model.Category
import br.notasocial.data.model.Social.Product
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.NotaSocialBottomAppBar
import br.notasocial.ui.NotaSocialTopAppBar
import br.notasocial.ui.components.product.ShopListItem
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.interFamily
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.viewmodel.consumidor.shoplist.ShopListViewModel

object ShopListDestination : NavigationDestination {
    override val route = "shop_list"
    override val title = "Lista de Produtos"
}

@Composable
fun ShopListScreen(
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
    viewModel: ShopListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val products = viewModel.products
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
                .verticalScroll(rememberScrollState())
                .background(color = Color.hsl(0f, 0f, .97f, 1f))
        ) {
            Column(
                modifier = Modifier
            ) {
                if (products.isEmpty()) {
                    EmptyShopList()
                } else {
                    ShopListGrid(
                        products = products
                    )
                    ShopListSummary(
                        products = products
                    )
                    ShopListDistance()
                }
            }
        }
    }
}

@Composable
fun EmptyShopList(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.basket_solid),
            contentDescription = "",
            tint = Color.hsl(123f, .63f, .33f, 1f),
            modifier = Modifier.size(96.dp)
        )
        Text(
            text = stringResource(id = R.string.empty_shop_list),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = ralewayFamily,
            color = Color.hsl(123f, .63f, .33f, 1f),
            modifier = Modifier.padding(vertical = 10.dp)
        )
        Text(
            text = stringResource(id = R.string.empty_shop_list_tip),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = ralewayFamily,
            textAlign = TextAlign.Center,
            lineHeight = 1.2.em,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Button(
            onClick = {},
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.hsl(123f, .63f, .33f, 1f),
                contentColor = Color.White
            ),
            modifier = Modifier.width(225.dp)
        ) {
            Text(
                text = stringResource(id = R.string.empty_shop_list_button_text).uppercase(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ralewayFamily,
                modifier = Modifier.padding(bottom = 3.dp)
            )
        }
        TextButton(
            onClick = {},
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.hsl(123f, .63f, .33f, 1f)
            ),
            border = BorderStroke(1.dp, color = Color.hsl(123f, .63f, .33f, 1f)),
            modifier = Modifier.width(225.dp)
        ) {
            Text(
                text = stringResource(id = R.string.empty_shop_list_button_text_alt).uppercase(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ralewayFamily,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 3.dp)
                    .fillMaxWidth(),
            )
        }
    }
}

@Composable
fun ShopListGrid(
    products: List<Product>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.shop_list_products_title),
            color = Color.Black,
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraLight,
            fontFamily = ralewayFamily,
            modifier = Modifier.padding(top = 12.dp, bottom = 8.dp, start = 15.dp)
        )
        products.forEach { product ->
            ShopListItem(
                product = product,
                modifier = Modifier.padding(vertical = 3.dp)
            )
        }
    }
}

@Composable
fun ShopListSummary(
    products: List<Product>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = stringResource(id = R.string.shop_list_summary_title),
            fontSize = 12.sp,
            color = Color.Black,
            fontWeight = FontWeight.ExtraLight,
            fontFamily = ralewayFamily,
            modifier = Modifier.padding(bottom = 8.dp, start = 15.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(15.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Subtotal (${products.size} itens)",
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = ralewayFamily
                )
                Text(
                    text = "R$${products.sumOf { it.price }}",
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontFamily = interFamily
                )
            }
            Divider(
                thickness = 3.dp,
                color = Color.hsl(0f, 0f, .97f, 1f),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Estabelecimento",
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = ralewayFamily
                )
                Text(
                    text = "Carrefour",
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontFamily = ralewayFamily
                )
            }

        }
    }
}

@Composable
fun ShopListDistance(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.shop_list_distance_title),
            fontSize = 12.sp,
            color = Color.Black,
            fontWeight = FontWeight.ExtraLight,
            fontFamily = ralewayFamily,
            modifier = Modifier.padding(bottom = 8.dp, start = 15.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(15.dp)
        ) {
            Text(
                text = "CEP",
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ralewayFamily,
                modifier = Modifier.padding(bottom = 3.dp)
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(35.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Estabelecimento",
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = ralewayFamily,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Preço",
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = ralewayFamily,
                    modifier = Modifier.width(80.dp)
                )
                Text(
                    text = "Distância",
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = ralewayFamily,
                    textAlign = TextAlign.End,
                    modifier = Modifier.width(80.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Angeloni",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = ralewayFamily,
                    textDecoration = TextDecoration.Underline,
                    color = Color.hsl(123f, .63f, .33f, 1f),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "R$53,80",
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = interFamily,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.width(80.dp)
                )
                Text(
                    text = "2 - KM",
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = interFamily,
                    textAlign = TextAlign.End,
                    modifier = Modifier.width(80.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Nacional",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = ralewayFamily,
                    textDecoration = TextDecoration.Underline,
                    color = Color.hsl(123f, .63f, .33f, 1f),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "R$55,80",
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = interFamily,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.width(80.dp)
                )
                Text(
                    text = "3.3 - KM",
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = interFamily,
                    textAlign = TextAlign.End,
                    modifier = Modifier.width(80.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Carrefour",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = ralewayFamily,
                    textDecoration = TextDecoration.Underline,
                    color = Color.hsl(123f, .63f, .33f, 1f),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "R$52,80",
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = interFamily,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.width(80.dp)
                )
                Text(
                    text = "6.7 - KM",
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = interFamily,
                    textAlign = TextAlign.End,
                    modifier = Modifier.width(80.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShopListScreenPreview() {
    NotasocialTheme {
        ShopListScreen(
            {}, {}, {}, {}, {}, {}, {}, {}, {}, {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShopListGridPreview() {
    val mockProducts = listOf(
        Product(
            id = 1,
            name = "Pao Forma Seven Boys",
            price = 6.69,
            category = Category(1, "Padaria", ""),
            image = R.drawable.pao_forma,
            isFavorite = true,
        ),
        Product(
            id = 2,
            name = "Pao Forma Seven Boys",
            price = 6.69,
            category = Category(1, "Padaria", ""),
            image = R.drawable.pao_forma,
            isFavorite = true,
        ),
        Product(
            id = 3,
            name = "Pao Forma Seven Boys",
            price = 6.69,
            category = Category(1, "Padaria", ""),
            image = R.drawable.pao_forma,
            isFavorite = true,
        ),
    )
    NotasocialTheme {
        ShopListGrid(products = mockProducts)
    }
}

@Preview(showBackground = true)
@Composable
fun ShopListSummaryPreview() {
    NotasocialTheme {
        val mockProducts = listOf(
            Product(
                id = 1,
                name = "Pao Forma Seven Boys",
                price = 6.69,
                category = Category(1, "Padaria", ""),
                image = R.drawable.pao_forma,
                isFavorite = true,
            ),
            Product(
                id = 2,
                name = "Pao Forma Seven Boys",
                price = 6.69,
                category = Category(1, "Padaria", ""),
                image = R.drawable.pao_forma,
                isFavorite = true,
            ),
            Product(
                id = 3,
                name = "Pao Forma Seven Boys",
                price = 6.69,
                category = Category(1, "Padaria", ""),
                image = R.drawable.pao_forma,
                isFavorite = true,
            ),
        )
        ShopListSummary(
            products = mockProducts
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShopListDistancePreview() {
    NotasocialTheme {
        ShopListDistance()
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyShopListPreview() {
    NotasocialTheme {
        EmptyShopList()
    }
}
