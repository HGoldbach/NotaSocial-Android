package br.notasocial.ui.view.store

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.data.model.StoreDb.PromotionDb
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.NotaSocialBottomAppBar
import br.notasocial.ui.NotaSocialTopAppBar
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.interFamily
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.view.customer.searchproduct.FilterSearch
import br.notasocial.ui.view.customer.searchproduct.SearchBar
import br.notasocial.ui.viewmodel.store.PromotionViewModel

object StorePromotionsDestination : NavigationDestination {
    override val route = "promocoes"
    override val title = "Promocoes"
}


@Composable
fun StorePromotionsScreen(
    navigateToPromotionDetails: (Int) -> Unit,
    navigateToPromotionEdit: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PromotionViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.hsl(0f, 0f, .97f, 1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
        ) {
            PromocoesTopSection(
                navigateToPromotionEdit = navigateToPromotionEdit,
                modifier = Modifier.padding(bottom = 15.dp)
            )
            PromocoesGrid(
                navigateToPromotionDetails = navigateToPromotionDetails,
                promocoes = viewModel.uiState.promotions
            )
        }
    }
}

@Composable
fun PromocoesTopSection(
    navigateToPromotionEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Promoções",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                fontFamily = ralewayFamily
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.clickable { navigateToPromotionEdit() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.square_plus_solid),
                    contentDescription = "",
                    tint = Color.hsl(123f, 0.66f, 0.33f, 1f),
                    modifier = Modifier.size(15.dp)
                )
                Text(
                    text = "Cadastrar Promoção".uppercase(),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = ralewayFamily,
                    color = Color.hsl(123f, 0.66f, 0.33f, 1f),
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            SearchBar(
                placeholderText = "Buscar Promoção",
                searchText = "",
                onSearchChange = {},
                searchProduct = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            FilterSearch()
        }
    }
}

@Composable
fun PromocoesGrid(
    navigateToPromotionDetails: (Int) -> Unit,
    promocoes: List<PromotionDb>,
    modifier: Modifier = Modifier
) {
    if (promocoes.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Nenhuma promoção cadastrada"
            )
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
        ) {
            items(items = promocoes, key = { it.id }) { promocao ->
                PromocaoItem(
                    promocao = promocao,
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable { navigateToPromotionDetails(promocao.id) }
                )
            }
        }
    }
}

@Composable
fun PromocaoItem(
    promocao: PromotionDb,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(color = Color.White, shape = RoundedCornerShape(15.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = promocao.title,
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            fontFamily = ralewayFamily,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.list_solid),
            contentDescription = "",
            tint = Color.hsl(123f, 0.66f, 0.33f, 1f),
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = "Validade",
            fontSize = 12.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            fontFamily = ralewayFamily,
            modifier = Modifier.padding(top = 12.dp)
        )
        Text(
            text = promocao.validity,
            color = Color.Black,
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = interFamily
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PromocoesScreenPreview() {
    NotasocialTheme {
        StorePromotionsScreen(
            {}, {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PromocoesTopSectionPreview() {
    NotasocialTheme {
        PromocoesTopSection(
            {}
        )
    }
}



