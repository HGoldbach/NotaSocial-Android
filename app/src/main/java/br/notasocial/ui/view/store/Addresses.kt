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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.data.model.StoreDb.AddressDb
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.components.store.StoreDialog
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.view.customer.searchproduct.FilterSearch
import br.notasocial.ui.view.customer.searchproduct.SearchBar
import br.notasocial.ui.viewmodel.store.AddressViewModel

object StoreAddressesDestination : NavigationDestination {
    override val route = "enderecos"
    override val title = "Endereços"
}

@Composable
fun StoreAddressesScreen(
    navigateToAddressEdit: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddressViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState
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
            EnderecosTopSection(
                navigateToAddressEdit = navigateToAddressEdit,
                modifier = Modifier.padding(bottom = 15.dp)
            )
            EnderecosGrid(
                addresses = uiState.addresses,
                onRemove = viewModel::removeAddress
            )
        }
    }
}

@Composable
fun EnderecosTopSection(
    navigateToAddressEdit: () -> Unit,
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
                text = "Endereços",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                fontFamily = ralewayFamily
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.clickable { navigateToAddressEdit() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.square_plus_solid),
                    contentDescription = "",
                    tint = Color.hsl(123f, 0.66f, 0.33f, 1f),
                    modifier = Modifier.size(15.dp)
                )
                Text(
                    text = "Cadastrar Endereço".uppercase(),
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
                placeholderText = "Buscar Endereço",
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
fun EnderecosGrid(
    modifier: Modifier = Modifier,
    addresses: List<AddressDb>,
    onRemove: (Int) -> Unit
) {
    if (addresses.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(text = "Nenhum endereço cadastrado")
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
        ) {
            items(items = addresses, key = { it.id }) { address ->
                EnderecoItem(
                    address = address,
                    onRemove = onRemove,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun EnderecoItem(
    modifier: Modifier = Modifier,
    onRemove: (Int) -> Unit,
    address: AddressDb
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .background(color = Color.White, shape = RoundedCornerShape(15.dp))
            .padding(12.dp)
            .clickable { showDialog = !showDialog },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Filial",
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            fontFamily = ralewayFamily,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.building_solid),
            contentDescription = "",
            tint = Color.hsl(123f, 0.66f, 0.33f, 1f),
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = address.street,
            fontSize = 12.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            fontFamily = ralewayFamily,
            minLines = 2,
            lineHeight = 1.2.em,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 12.dp)
                .width(100.dp)
        )
        if (showDialog) {
            StoreDialog(
                address = address,
                onDismissRequest = {
                    showDialog = !showDialog
                },
                onRemove = onRemove
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EnderecosScreenPreview() {
    NotasocialTheme {
        StoreAddressesScreen(
            {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EnderecosTopSectionPreview() {
    NotasocialTheme {
        EnderecosTopSection(
            {}
        )
    }
}
