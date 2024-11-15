package br.notasocial.ui.view.consumidor.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.ui.NotaSocialBottomAppBar
import br.notasocial.ui.NotaSocialTopAppBar
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val title = "Home"
}

data class Categoria(val id: Int, val nome: String)

data class Produto(
    val id: Int,
    val nome: String,
    val imagem: Int,
    var isFavorite: Boolean,
    val preco: Double
)

val categorias = listOf(
    Categoria(1, "Alimentos"),
    Categoria(2, "Feira"),
    Categoria(3, "Bebidas"),
    Categoria(4, "Carnes"),
    Categoria(5, "Frutas"),
    Categoria(6, "Verduras"),
    Categoria(7, "Padaria"),
    Categoria(8, "Laticínios"),
    Categoria(9, "Higiene"),
    Categoria(10, "Limpeza"),
    Categoria(11, "Outros")
)



val produtos = listOf(
    Produto(
        1, "Pao Forma Seven Boys", R.drawable.pao_forma, true, 6.69
    ),
    Produto(
        2, "Mamão Formosa", R.drawable.mamao_formosa, false, 12.50
    ),
    Produto(
        3, "Arroz TP1 Buriti", R.drawable.arroz_buriti, false, 6.69
    )
)

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
    modifier: Modifier = Modifier
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
                categorias = categorias
            )
            Spacer(modifier = Modifier.height(25.dp))
            LastUpdatesSection(
                produtos = produtos
            )
        }

    }
}

@Composable
fun LastUpdatesSection(
    produtos: List<Produto>,
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
                text = "Últimas Atualizações",
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
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(items = produtos, key = { it.id }) { produto ->
            ProductItem(
                produto,
                Modifier
                    .padding(8.dp)
                    .background(Color.White, shape = RoundedCornerShape(15.dp))
                    .padding(10.dp)
            )
        }
    }
}

@Composable
fun ProductItem(
    produto: Produto,
    modifier: Modifier = Modifier
) {
    var isFavorite by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
            .width(120.dp),
        horizontalAlignment = Alignment.End
    ) {
        Icon(
            painter = if (isFavorite) {
                painterResource(R.drawable.heart_solid)
            } else {
                painterResource(R.drawable.heart_regular)
            },
            contentDescription = "",
            tint = if (isFavorite) {
                Color.hsl(0f, 1f, .44f, 1f)
            } else {
                Color.Gray
            },
            modifier = Modifier
                .size(18.dp)
                .clickable { isFavorite = !isFavorite }
        )
        Image(
            painter = painterResource(produto.imagem),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = produto.nome,
            fontFamily = ralewayFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
            lineHeight = 1.em
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "R$${produto.preco}",
                fontFamily = ralewayFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
            IconButton(
                onClick = {},
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.hsl(128f, .52f, .47f, .2f)
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.cart_shopping_solid),
                    contentDescription = "",
                    tint = Color.hsl(123f, .63f, .33f, 1f),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun CategorySection(
    modifier: Modifier = Modifier,
    categorias: List<Categoria>
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
                text = "Categorias",
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
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 5.dp)
    ) {
        items(items = categorias, key = { it.id }) { categoria ->
            CategoryItem(categoria)
        }
    }
}

@Composable
fun CategoryItem(
    categoria: Categoria,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Surface(
            modifier = modifier
                .size(100.dp)
                .padding(10.dp),
            shape = CircleShape,
            color = Color.White
        ) {}
        Text(
            text = categoria.nome,
            color = Color.Black,
            fontFamily = ralewayFamily,
            fontWeight = FontWeight.SemiBold,
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
                text = "Buscar Produto",
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
fun NotaSocialTitle(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "NOTA SOCIAL",
            color = Color.hsl(128f, .52f, .47f, 1f),
            fontFamily = ralewayFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp
        )
        Text(
            text = "Compartilhe, compare, economize",
            color = Color.Black,
            fontFamily = ralewayFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        )
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
        CategorySection(
            categorias = categorias
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