package br.notasocial.ui.view.customer.shoplist

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.data.model.Catalog.Category
import br.notasocial.data.model.Catalog.Product
import br.notasocial.data.model.Social.ShoppingListResponse
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.components.product.ShopListItem
import br.notasocial.ui.components.store.InputField
import br.notasocial.ui.components.store.MaskVisualTransformation
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.interFamily
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.utils.formatDistance
import br.notasocial.ui.utils.formatPrice
import br.notasocial.ui.utils.textTitleCase
import br.notasocial.ui.viewmodel.customer.shoplist.ShopListViewModel
import java.util.Locale

object ShopListDestination : NavigationDestination {
    override val route = "shop_list"
    override val title = "Lista de Produtos"
}



@Composable
fun ShopListScreen(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    viewModel: ShopListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = Color.hsl(0f, 0f, .97f, 1f))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            if (viewModel.products.isEmpty()) {
                EmptyShopList(
                    navigateToHome = navigateToHome
                )
            } else {
                ShopListGrid(
                    onRemoveClick = viewModel::removeProductsToShoppingList,
                    products = viewModel.products
                )
                ShopListSummary(
                    products = viewModel.products
                )
                ShopListDistance(
                    cep = viewModel.cep,
                    shoppingListInfo = viewModel.shoppingListInfo,
                    onCalculateDistance = viewModel::calculateDistance,
                    onCepChange = viewModel::onCepChange,
                    valueSum = viewModel.products.sumOf { it.price!! }
                )
            }
        }
    }
}

@Composable
fun EmptyShopList(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit
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
            onClick = navigateToHome,
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
    onRemoveClick: (String) -> Unit,
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
                modifier = Modifier.padding(vertical = 3.dp),
                onRemoveClick = onRemoveClick
            )
        }
    }
}

@Composable
fun ShopListSummary(
    products: List<Product>,
    modifier: Modifier = Modifier
) {
    val formattedPrice = String.format(Locale("pt", "BR"), "R$%.2f", products.sumOf { it.price!! })
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
                    text = formattedPrice,
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontFamily = interFamily
                )
            }
        }
    }
}

@Composable
fun ShopListDistance(
    modifier: Modifier = Modifier,
    cep: String,
    onCepChange: (String) -> Unit,
    onCalculateDistance: () -> Unit,
    shoppingListInfo: ShoppingListResponse?,
    valueSum: Double,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                InputField(
                    text = "CEP",
                    value = cep,
                    onValueChange = {
                        if (it.length <= NumberDefaults.INPUT_LENGTH) {
                            onCepChange(it)
                        }
                    },
                    visualTransformation = MaskVisualTransformation(NumberDefaults.MASK),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .weight(2f)
                        .padding(bottom = 20.dp)
                )
                Button(
                    onClick = onCalculateDistance,
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .padding(start = 10.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.hsl(123f, .63f, .33f, 1f)
                    )
                ) {
                    Text(
                        text = "Buscar",
                        fontFamily = ralewayFamily,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
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
            shoppingListInfo?.content?.forEach {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = textTitleCase(it.branch.store.name),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = ralewayFamily,
                        textDecoration = TextDecoration.Underline,
                        color = Color.hsl(123f, .63f, .33f, 1f),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = formatPrice(valueSum),
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = interFamily,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.width(80.dp)
                    )
                    Text(
                        text = formatDistance(it.branch.distance),
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
}

object NumberDefaults {
    const val MASK = "#####-###"
    const val INPUT_LENGTH = 8 // Equals to "#####-###".count { it == '#' }
}

//@Preview(showBackground = true)
//@Composable
//fun ShopListGridPreview() {
//    val mockProducts = listOf(
//        Product(
//            id = "1",
//            name = "Pao Forma Seven Boys",
//            price = 6.69,
//            category = Category(1, "Padaria", ""),
//            image = "",
//            code = "",
//            storeId = "",
//            unit = ""
//        ),
//        Product(
//            id = "2",
//            name = "Pao Forma Seven Boys",
//            price = 6.69,
//            category = Category(1, "Padaria", ""),
//            image = "",
//            code = "",
//            storeId = "",
//            unit = ""
//        ),
//        Product(
//            id = "3",
//            name = "Pao Forma Seven Boys",
//            price = 6.69,
//            category = Category(1, "Padaria", ""),
//            image = "",
//            code = "",
//            storeId = "",
//            unit = ""
//        ),
//    )
//    NotasocialTheme {
//        ShopListGrid(
//            products = mockProducts,
//            onRemoveClick = {}
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ShopListSummaryPreview() {
//    NotasocialTheme {
//        val mockProducts = listOf(
//            Product(
//                id = "1",
//                name = "Pao Forma Seven Boys",
//                price = 6.69,
//                category = Category(1, "Padaria", ""),
//                image = "",
//                code = "",
//                storeId = "",
//                unit = ""
//            ),
//            Product(
//                id = "2",
//                name = "Pao Forma Seven Boys",
//                price = 6.69,
//                category = Category(1, "Padaria", ""),
//                image = "",
//                code = "",
//                storeId = "",
//                unit = ""
//            ),
//            Product(
//                id = "3",
//                name = "Pao Forma Seven Boys",
//                price = 6.69,
//                category = Category(1, "Padaria", ""),
//                image = "",
//                code = "",
//                storeId = "",
//                unit = ""
//            ),
//        )
//        ShopListSummary(
//            products = mockProducts
//        )
//    }
//}


@Preview(showBackground = true)
@Composable
fun EmptyShopListPreview() {
    NotasocialTheme {
        EmptyShopList(navigateToHome = {})
    }
}
