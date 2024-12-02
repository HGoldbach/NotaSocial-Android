package br.notasocial.ui.view.store

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.data.model.StoreDb.PromotionDb
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.interFamily
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.viewmodel.store.PromotionDetailsViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.util.Locale

object StorePromotionDetailsDestination : NavigationDestination {
    override val route = "promocao_info"
    override val title = "Promocao Info"
    const val promotionIdArg = "promotionId"
    val routeWithArgs = "${StorePromotionDetailsDestination.route}/{$promotionIdArg}"
}

data class PromocaoItem(
    val id: Int,
    val nome: String,
    val preco: Double
)

val promocoesInfo = listOf(
    PromocaoItem(1, "Mamao Formosa", 8.20),
    PromocaoItem(2, "Alface", 4.50),
    PromocaoItem(3, "Tomate", 3.20),
    PromocaoItem(4, "Banana Prata", 2.50),
    PromocaoItem(5, "Laranja Lima", 5.20),
)

@Composable
fun PromotionDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: PromotionDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.hsl(0f, 0f, .97f, 1f))
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            PromocaoInfoTopSection(
                onDeletePromotion = viewModel::removePromotion,
                promotion = uiState.promotion,
            )
            PromocaoInfoGrid(
                products = uiState.products,
                validity = uiState.promotion.validity,
                modifier = Modifier.padding(top = 30.dp)
            )
        }
    }
}

@Composable
fun PromocaoInfoTopSection(
    onDeletePromotion: () -> Unit,
    modifier: Modifier = Modifier,
    promotion: PromotionDb
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.size(140.dp),
            color = Color.White,
            shape = CircleShape
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data("https://static.vecteezy.com/ti/vetor-gratis/p1/5766127-supermercado-loja-logo-vetor.jpg")
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxWidth().height(80.dp)
            )
        }
        Text(
            text = "Promoção ${promotion.title}",
            fontFamily = ralewayFamily,
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = "Endereço - ${promotion.establishment}",
            fontFamily = ralewayFamily,
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Normal,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.Absolute.Center
        ) {
            Button(
                onClick = {},
                modifier = Modifier
                    .width(125.dp)
                    .height(35.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.hsl(123f, 0.66f, 0.33f, 1f),
                    contentColor = Color.White
                )

            ) {
                Text(
                    text = "Editar",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = ralewayFamily,
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Button(
                onClick = onDeletePromotion,
                modifier = Modifier
                    .width(125.dp)
                    .height(35.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.hsl(0f, 1f, .5f, 1f),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Excluir",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = ralewayFamily
                )
            }
        }
    }
}

@Composable
fun PromocaoInfoGrid(
    modifier: Modifier = Modifier,
    validity: String,
    products: List<Pair<String, String>>,
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
            modifier = Modifier.padding(bottom = 30.dp)
        )
        LazyColumn() {
            items(items = products, key = { it.first }) { product ->
                PromocaoInfoItem(
                    product = product,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }
        }
        Text(
            text = "Promoção válida até $validity",
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontFamily = ralewayFamily,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
        )
    }
}

@Composable
fun PromocaoInfoItem(
    modifier: Modifier = Modifier,
    product: Pair<String, String>
) {
    val formattedPrice = String.format(Locale("pt", "BR"), "R$%.2f", product.second.toDouble())
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
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
            text = formattedPrice,
            fontSize = 13.sp,
            color = Color.Black,
            fontWeight = FontWeight.Light,
            fontFamily = interFamily
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PromocaoInfoScreenPreview() {
    NotasocialTheme {
        PromotionDetailsScreen()
    }
}
