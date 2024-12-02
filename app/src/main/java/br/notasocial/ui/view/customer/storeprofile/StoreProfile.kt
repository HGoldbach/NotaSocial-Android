package br.notasocial.ui.view.customer.storeprofile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.data.model.Store
import br.notasocial.data.model.StoreDb.AddressDb
import br.notasocial.data.model.StoreDb.PromotionDb
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.components.store.StoreAddressItem
import br.notasocial.ui.components.store.StoreInfo
import br.notasocial.ui.components.store.StoreMenu
import br.notasocial.ui.components.store.StorePromotionItem
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.viewmodel.customer.storeprofile.StoreProfileViewModel

object StoreProfileDestination : NavigationDestination {
    override val route = "store_profile"
    override val title = "Estabelecimento Perfil"
    const val storeIdArg = "storeId"
    val routeWithArgs = "$route/{$storeIdArg}"
}

enum class StoreMenuItem {
    PROMOCOES,
    ENDERECOS
}

@Composable
fun StoreProfileScreen(
    navigateToPromotion: (Int, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StoreProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var selectedMenuItem by remember {
        mutableStateOf(StoreMenuItem.PROMOCOES)
    }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(color = Color.hsl(0f, 0f, .97f, 1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            StoreProfileTopSection(
                store = viewModel.store
            )
            StoreMenu(
                selectedMenuItem = selectedMenuItem,
                onMenuItemClick = { selectedMenuItem = it },
                modifier = Modifier.padding(vertical = 20.dp)
            )
            when (selectedMenuItem) {
                StoreMenuItem.PROMOCOES -> PromotionsSection(
                    promotions = viewModel.promotions,
                    navigateToPromotion = navigateToPromotion,
                    storeName = "${viewModel.store.name}"
                )

                StoreMenuItem.ENDERECOS -> AddressSection(
                    addresses = viewModel.addresses
                )
            }
        }
    }
}

@Composable
fun StoreProfileTopSection(
    modifier: Modifier = Modifier,
    store: Store
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
            title = "${store.name}".uppercase(),
        )
    }
}

@Composable
fun PromotionsSection(
    navigateToPromotion: (Int, String) -> Unit,
    modifier: Modifier = Modifier,
    storeName: String,
    promotions: List<PromotionDb>
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
        if (promotions.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Nenhuma promoção cadastrada",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = ralewayFamily
                )
            }
        } else {
            promotions.forEach {
                StorePromotionItem(
                    promotion = it,
                    navigateToPromotion = navigateToPromotion,
                    storeName = storeName
                )
                Divider(
                    thickness = 5.dp,
                    color = Color.hsl(0f, 0f, .97f, 1f),
                    modifier = Modifier.width(100.dp)
                )
            }
        }
    }
}

@Composable
fun AddressSection(
    modifier: Modifier = Modifier,
    addresses: List<AddressDb>
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
        if (addresses.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Nenhum endereço cadastrado",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = ralewayFamily
                )
            }
        } else {
            AddressGrid(
                addresses = addresses
            )
        }
    }
}

@Composable
fun AddressGrid(
    addresses: List<AddressDb>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.height(500.dp)
    ) {
        items(items = addresses, key = { it.id }) { address ->
            StoreAddressItem(
                address = address,
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
            {_, _ ->}
        )
    }
}


@Preview(showBackground = true)
@Composable
fun StoreProfileTopSectionPreview() {
    NotasocialTheme {
        StoreProfileTopSection(store = Store(name = "Carrefour"))
    }
}

@Preview(showBackground = true)
@Composable
fun PromotionsSectionPreview() {
    NotasocialTheme {
        PromotionsSection(
            {_, _ ->},
            promotions = emptyList(),
            storeName = ""
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddressSectionPreview() {
    NotasocialTheme {
        AddressSection(addresses = emptyList())
    }
}