package br.notasocial.ui.view.consumidor.storeprofile

import android.location.Address
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.ui.NotaSocialBottomAppBar
import br.notasocial.ui.NotaSocialTopAppBar
import br.notasocial.ui.components.store.StoreAddressItem
import br.notasocial.ui.components.store.StoreInfo
import br.notasocial.ui.components.store.StoreMenu
import br.notasocial.ui.components.store.StorePromotionItem
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily

object StoreProfileDestination : NavigationDestination {
    override val route = "store_profile"
    override val title = "Estabelecimento Perfil"
}

enum class StoreMenuItem {
    PROMOCOES,
    ENDERECOS
}

// Provisorio
data class Endereco(
    val id: Int,
    val estabelecimento: String,
    val logo: Int,
    val rua: String,
    val numero: String,
    val bairro: String,
    val cidade: String,
    val estado: String,
    val cep: String,
    val telefone: String,
)

val enderecos = listOf(
    Endereco(
        id = 1,
        estabelecimento = "Carrefour",
        logo = R.drawable.carrefour_logo,
        rua = "Av. Mal. Floriano Peixoto",
        numero = "3031",
        bairro = "Rebouças",
        cidade = "Curitiba",
        estado = "PR",
        cep = "80220-000",
        telefone = "(11) 3004-2222"
    ),
    Endereco(
        id = 2,
        estabelecimento = "Carrefour",
        logo = R.drawable.carrefour_logo,
        rua = "Av. Paraná",
        numero = "3031",
        bairro = "Rebouças",
        cidade = "Curitiba",
        estado = "PR",
        cep = "80220-000",
        telefone = "(11) 3004-2222"
    ),
    Endereco(
        id = 3,
        estabelecimento = "Carrefour",
        logo = R.drawable.carrefour_logo,
        rua = "R. Dep. Heitor Alencar Furtado",
        numero = "3031",
        bairro = "Rebouças",
        cidade = "Curitiba",
        estado = "PR",
        cep = "80220-000",
        telefone = "(11) 3004-2222"
    ),
    Endereco(
        id = 4,
        estabelecimento = "Carrefour",
        logo = R.drawable.carrefour_logo,
        rua = "Av. Presidente Arthur",
        numero = "3031",
        bairro = "Rebouças",
        cidade = "Curitiba",
        estado = "PR",
        cep = "80220-000",
        telefone = "(11) 3004-2222"
    ),
    Endereco(
        id = 5,
        estabelecimento = "Carrefour",
        logo = R.drawable.carrefour_logo,
        rua = "Av. Mal. Floriano Peixoto",
        numero = "3031",
        bairro = "Rebouças",
        cidade = "Curitiba",
        estado = "PR",
        cep = "80220-000",
        telefone = "(11) 3004-2222"
    ),
    Endereco(
        id = 6,
        estabelecimento = "Carrefour",
        logo = R.drawable.carrefour_logo,
        rua = "Av. Paraná",
        numero = "3031",
        bairro = "Rebouças",
        cidade = "Curitiba",
        estado = "PR",
        cep = "80220-000",
        telefone = "(11) 3004-2222"
    ),
    Endereco(
        id = 7,
        estabelecimento = "Carrefour",
        logo = R.drawable.carrefour_logo,
        rua = "R. Dep. Heitor Alencar Furtado",
        numero = "3031",
        bairro = "Rebouças",
        cidade = "Curitiba",
        estado = "PR",
        cep = "80220-000",
        telefone = "(11) 3004-2222"
    ),
    Endereco(
        id = 8,
        estabelecimento = "Carrefour",
        logo = R.drawable.carrefour_logo,
        rua = "Av. Presidente Arthur",
        numero = "3031",
        bairro = "Rebouças",
        cidade = "Curitiba",
        estado = "PR",
        cep = "80220-000",
        telefone = "(11) 3004-2222"
    ),
)

@Composable
fun StoreProfileScreen(
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
    navigateToEstabelecimentoPromocao: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedMenuItem by remember {
        mutableStateOf(StoreMenuItem.PROMOCOES)
    }
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
                .padding(it)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(color = Color.hsl(0f, 0f, .97f, 1f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                StoreProfileTopSection()
                StoreMenu(
                    selectedMenuItem = selectedMenuItem,
                    onMenuItemClick = { selectedMenuItem = it },
                    modifier = Modifier.padding(vertical = 20.dp)
                )
                when (selectedMenuItem) {
                    StoreMenuItem.PROMOCOES -> PromotionsSection(
                        navigateToPromotion = navigateToEstabelecimentoPromocao,
                    )
                    StoreMenuItem.ENDERECOS -> AddressSection()
                }
            }
        }
    }
}

@Composable
fun StoreProfileTopSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            TextButton(
                onClick = {},
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.White,
                ),
                border = BorderStroke(1.dp, color = Color.hsl(123f, .63f, .33f, 1f)),
                modifier = Modifier
                    .width(82.dp)
                    .height(35.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.follow_profile).uppercase(),
                    fontFamily = ralewayFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.hsl(123f, .63f, .33f, 1f),
                )
            }
        }
        StoreInfo(
            title = "Carrefour".uppercase(),
            description = "Estabelecimentos: 8"
        )
    }
}

@Composable
fun PromotionsSection(
    navigateToPromotion: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
    ) {
        Text(
            text = stringResource(id = R.string.promotion_menu_item),
            fontFamily = ralewayFamily,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)
        )
        Divider(
            thickness = 5.dp,
            color = Color.hsl(0f, 0f, .97f, 1f)
        )
        StorePromotionItem(
            navigateToPromotion = navigateToPromotion
        )
        Divider(
            thickness = 5.dp,
            color = Color.hsl(0f, 0f, .97f, 1f),
            modifier = Modifier.width(100.dp)
        )
        StorePromotionItem(
            navigateToPromotion = navigateToPromotion
        )

    }
}

@Composable
fun AddressSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
    ) {
        Text(
            text = stringResource(id = R.string.address_menu_item),
            fontFamily = ralewayFamily,
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)
        )
        Divider(
            thickness = 5.dp,
            color = Color.hsl(0f, 0f, .97f, 1f)
        )
        AddressGrid(
            address = enderecos
        )
    }
}

@Composable
fun AddressGrid(
    address: List<Endereco>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.height(500.dp)
    ) {
        items(items = address, key = { it.id }) { endereco ->
            StoreAddressItem(
                endereco = endereco,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun StoreProfileScreenPreview() {
    NotasocialTheme {
        StoreProfileScreen(
            {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}
        )
    }
}


@Preview(showBackground = true)
@Composable
fun StoreProfileTopSectionPreview() {
    NotasocialTheme {
        StoreProfileTopSection()
    }
}

@Preview(showBackground = true)
@Composable
fun PromotionsSectionPreview() {
    NotasocialTheme {
        PromotionsSection(
            {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddressSectionPreview() {
    NotasocialTheme {
        AddressSection()
    }
}