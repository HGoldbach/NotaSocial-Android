package br.notasocial.ui.view.customer.userprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.data.model.Receipt.Receipt
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.components.receipt.ReceiptItem
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.view.customer.searchproduct.FilterSearch
import br.notasocial.ui.view.customer.searchproduct.SearchBar
import br.notasocial.ui.viewmodel.customer.userprofile.ReceiptsViewModel

object PerfilNotasDestination : NavigationDestination {
    override val route = "perfil_notas"
    override val title = "Notas"
}

@Composable
fun PerfilNotasScreen(
    modifier: Modifier = Modifier,
    viewModel: ReceiptsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Column(
        modifier = modifier
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
            NotasGrid(
                receipts = viewModel.receipts
            )
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
    }
}

@Composable
fun NotasGrid(
    modifier: Modifier = Modifier,
    receipts: List<Receipt>
) {
    if (receipts.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Nenhuma nota cadastrada",
                color = Color.Black,
                fontSize = 14.sp,
                fontFamily = ralewayFamily,
                fontWeight = FontWeight.Medium
            )
        }
    } else {
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(2),
        ) {
            items(items = receipts, key = { it.scannedAt }) { receipt ->
                ReceiptItem(
                    receipt = receipt,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PerfilNotasScreenPreview() {
    NotasocialTheme {
        PerfilNotasScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun NotasTopSectionPreview() {
    NotasocialTheme {
        NotasTopSection()
    }
}
