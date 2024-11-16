package br.notasocial.ui.view.consumidor.userprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.notasocial.R
import br.notasocial.ui.NotaSocialBottomAppBar
import br.notasocial.ui.NotaSocialTopAppBar
import br.notasocial.ui.components.receipt.ReceiptItem
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.interFamily
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.view.consumidor.buscarproduto.FilterSearch
import br.notasocial.ui.view.consumidor.buscarproduto.SearchBar

object PerfilNotasDestination : NavigationDestination {
    override val route = "perfil_notas"
    override val title = "Notas"
}

@Composable
fun PerfilNotasScreen(
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
                .background(color = Color.hsl(0f, 0f, .97f, 1f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp)
            ) {
                NotasTopSection()
                Spacer(modifier = Modifier.padding(10.dp))
                NotasGrid()
            }
        }
    }
}

@Composable
fun NotasTopSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Notas",
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            fontFamily = ralewayFamily,
            modifier = Modifier.padding(vertical = 15.dp)
        )
        Row {
            SearchBar(
                placeholderText = "Buscar Nota",
                searchText = "",
                onSearchChange = {},
                searchProduct = {},
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            FilterSearch()
        }
    }
}

@Composable
fun NotasGrid(
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
    ) {
        items(10) {
            ReceiptItem(
                modifier = Modifier
                    .padding(5.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PerfilNotasScreenPreview() {
    NotasocialTheme {
        PerfilNotasScreen(
            {}, {}, {}, {}, {}, {}, {}, {}, {}, {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NotasTopSectionPreview() {
    NotasocialTheme {
        NotasTopSection()
    }
}

@Preview(showBackground = true)
@Composable
fun NotasGridPreview() {
    NotasocialTheme {
        NotasGrid()
    }
}

