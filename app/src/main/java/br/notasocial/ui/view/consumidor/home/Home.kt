package br.notasocial.ui.view.consumidor.home

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.data.model.Category
import br.notasocial.data.model.Social.Product
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.NotaSocialBottomAppBar
import br.notasocial.ui.NotaSocialTopAppBar
import br.notasocial.ui.components.category.CategoryItem
import br.notasocial.ui.components.product.ProductItem
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.viewmodel.consumidor.home.HomeViewModel

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val title = "Home"
}

@Composable
fun HomeScreen(
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
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val scrollState = rememberScrollState()
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
                .background(color = Color.hsl(0f, 0f, .97f, 1f))
                .padding(it)
                .verticalScroll(scrollState)
        ) {
            NotaSocialTitle()
            Spacer(modifier = Modifier.height(25.dp))
            SlideSection()
            Spacer(modifier = Modifier.height(25.dp))
            CategorySection(
                categories = viewModel.categories
            )
            Spacer(modifier = Modifier.height(25.dp))
            LastUpdatesSection(
                products = viewModel.products
            )

        }
    }
}

@Composable
fun NotaSocialTitle(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
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
    modifier: Modifier = Modifier,
    categories: List<Category>
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
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 5.dp)
    ) {
        items(items = categories, key = { it.id }) { category ->
            CategoryItem(
                text = category.name
            )
        }
    }
}

@Composable
fun LastUpdatesSection(
    products: List<Product>,
    modifier: Modifier = Modifier
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
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(items = products, key = { it.id }) { product ->
            ProductItem(
                product,
                Modifier
                    .padding(8.dp)
                    .background(Color.White, shape = RoundedCornerShape(15.dp))
                    .padding(10.dp)
            )
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
fun CategorySectionPreview() {
    NotasocialTheme {
        val mockCategories = listOf(
            Category(1, "Frutas", ""),
            Category(2, "Verduras", ""),
            Category(3, "Carnes", ""),
            Category(4, "Laticínios", ""),
            Category(5, "Doces", ""),
            Category(6, "Outros", "")
        )
        CategorySection(
            categories = mockCategories
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NotasocialTheme {
        HomeScreen(
            {}, {}, {}, {}, {}, {}, {}, {}, {}, {}
        )
    }
}