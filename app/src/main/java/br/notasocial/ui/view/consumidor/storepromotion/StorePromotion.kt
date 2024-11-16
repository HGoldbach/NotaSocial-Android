package br.notasocial.ui.view.consumidor.storepromotion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.notasocial.ui.NotaSocialBottomAppBar
import br.notasocial.ui.NotaSocialTopAppBar
import br.notasocial.ui.components.store.StoreInfo
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.interFamily
import br.notasocial.ui.theme.ralewayFamily

object StorePromotionDestination : NavigationDestination {
    override val route = "store_promotion"
    override val title = "Estabelecimento Promoção"
}

// Provisorio
data class ProductPromotion(
    val id: Int,
    val nome: String,
    val preco: Double
)

val promotions = listOf(
    ProductPromotion(
        id = 1,
        nome = "Pao Forma Seven Boys",
        preco = 2.99
    ),
    ProductPromotion(
        id = 2,
        nome = "Mamao Formosa",
        preco = 8.29
    ),
    ProductPromotion(
        id = 3,
        nome = "Coca Cola 2L",
        preco = 7.99
    ),
    ProductPromotion(
        id = 4,
        nome = "Limao Tahiti",
        preco = 3.99
    ),
    ProductPromotion(
        id = 5,
        nome = "Arroz Buriti",
        preco = 6.99
    ),
    ProductPromotion(
        id = 6,
        nome = "Pao Forma Seven Boys",
        preco = 2.99
    ),
    ProductPromotion(
        id = 7,
        nome = "Mamao Formosa",
        preco = 8.29
    ),
    ProductPromotion(
        id = 8,
        nome = "Coca Cola 2L",
        preco = 7.99
    ),
    ProductPromotion(
        id = 9,
        nome = "Limao Tahiti",
        preco = 3.99
    ),
    ProductPromotion(
        id = 10,
        nome = "Arroz Buriti",
        preco = 6.99
    ),
)

@Composable
fun StorePromotionScreen(
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
    modifier: Modifier = Modifier
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
           modifier = modifier
               .padding(it)
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
                    title = "Promoçao Carrefour",
                    description = "Endereço - Av. Mal. Floriano Peixoto"
                )
                ProductPromotionSection(
                    produtos = promotions
                )
            }
       }
   }
}

@Composable
fun ProductPromotionSection(
    produtos: List<ProductPromotion>,
    modifier: Modifier = Modifier
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
        produtos.forEach { produto ->
            ProductPromotionItem(
                produto = produto
            )
        }
        Text(
            text = "Promoção válida até 05 Maio 2024",
            fontSize = 16.sp,
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
    produto: ProductPromotion,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = produto.nome,
            fontSize = 13.sp,
            color = Color.Black,
            fontWeight = FontWeight.Normal,
            fontFamily = ralewayFamily
        )
        Text(
            text = "R$${produto.preco}",
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
        StorePromotionScreen(
            {}, {}, {}, {}, {}, {}, {}, {}, {}, {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductPromotionSectionPreview() {
    NotasocialTheme {
        ProductPromotionSection(
            produtos = promotions
        )
    }
}