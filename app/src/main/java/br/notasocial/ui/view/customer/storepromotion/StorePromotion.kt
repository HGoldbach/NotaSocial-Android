package br.notasocial.ui.view.customer.storepromotion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.data.model.Store
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.components.store.StoreInfo
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.interFamily
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.utils.formatDate
import br.notasocial.ui.utils.formatPriceString
import br.notasocial.ui.viewmodel.customer.storepromotion.StorePromotionViewModel

object StorePromotionDestination : NavigationDestination {
    override val route = "store_promotion"
    override val title = "Estabelecimento Promoção"
    const val promotionIdArg = "promotionId"
    const val storeNameArg = "storeName"
    val routeWithArgs = "${route}/{$promotionIdArg}/{$storeNameArg}"
}

@Composable
fun StorePromotionScreen(
    modifier: Modifier = Modifier,
    viewModel: StorePromotionViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = Color.hsl(0f, 0f, .97f, 1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            StoreInfo(
                title = "Promoçao ${viewModel.storeName}",
                store = Store(),
            )
            ProductPromotionSection(
                products = uiState.products,
                validity = uiState.promotion.validity,
            )
        }
    }
}

@Composable
fun ProductPromotionSection(
    modifier: Modifier = Modifier,
    products: List<Pair<String, String>>,
    validity: String,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Produtos",
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontFamily = ralewayFamily,
            modifier = Modifier.padding(vertical = 40.dp)
        )
        products.forEach { product ->
            ProductPromotionItem(
                product = product,
            )
        }
        Text(
            text = "Promoção válida até ${formatDate(validity)}",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontFamily = ralewayFamily,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
        )
    }
}

@Composable
fun ProductPromotionItem(
    modifier: Modifier = Modifier,
    product: Pair<String, String>
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = product.first,
            fontSize = 13.sp,
            color = Color.Black,
            fontWeight = FontWeight.Normal,
            fontFamily = ralewayFamily
        )
        Text(
            text = formatPriceString(product.second),
            fontSize = 13.sp,
            color = Color.Black,
            fontWeight = FontWeight.Light,
            fontFamily = interFamily,
        )
    }

}

@Preview(showBackground = true)
@Composable
fun StorePromotionScreenPreview() {
    NotasocialTheme {
        StorePromotionScreen()
    }
}
