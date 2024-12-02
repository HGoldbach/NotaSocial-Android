package br.notasocial.ui.view.store

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.components.store.InputField
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.viewmodel.store.PromotionUiState
import br.notasocial.ui.viewmodel.store.PromotionViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest

object StorePromotionEditDestination : NavigationDestination {
    override val route = "promotion_edit"
    override val title = "promotion_edit"
}

@Composable
fun StorePromotionEditScreen(
    viewModel: PromotionViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val uiState = viewModel.uiState

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(color = Color.hsl(0f, 0f, .97f, 1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
        ) {
            PromotionEditTopSection()
            PromotionFormSection(
                uiState = uiState,
                onTitleChange = viewModel::onTitleChange,
                onDueDateChange = viewModel::onDueDateChange,
                onStoreChange = viewModel::onStoreChange,
                onAddProduct = viewModel::onAddProduct,
                onProductNameChange = viewModel::onProductNameChange,
                onProductPriceChange = viewModel::onProductPriceChange,
                onSave = viewModel::savePromotion
            )
        }
    }
}

@Composable
fun PromotionEditTopSection(
    modifier: Modifier = Modifier
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
            text = "Promoção",
            fontFamily = ralewayFamily,
            fontSize = 24.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@Composable
fun PromotionFormSection(
    uiState: PromotionUiState,
    onTitleChange: (String) -> Unit,
    onDueDateChange: (String) -> Unit,
    onStoreChange: (String) -> Unit,
    onProductNameChange: (Int, String) -> Unit,
    onProductPriceChange: (Int, String) -> Unit,
    onAddProduct: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        InputField(
            text = "Título",
            value = uiState.title,
            onValueChange = onTitleChange,
            modifier = Modifier.padding(top = 30.dp)
        )
        InputField(
            text = "Validade",
            value = uiState.dueDate,
            onValueChange = onDueDateChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            icon = R.drawable.calendar_solid,
            modifier = Modifier
                .padding(vertical = 12.dp)
        )
        InputField(
            text = "Estabelecimento",
            value = uiState.store,
            onValueChange = onStoreChange,
            modifier = Modifier.fillMaxWidth()
        )
        Column {
            Text(
                text = "Produtos",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                fontFamily = ralewayFamily,
                modifier = Modifier.padding(vertical = 12.dp)
            )
            // Itera pelos produtos e cria um campo para cada um
            uiState.products.forEachIndexed { index, product ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    InputField(
                        text = "Nome", // Nome
                        value = product.first,
                        onValueChange = { name -> onProductNameChange(index, name) },
                        modifier = Modifier
                            .weight(2f)
                            .padding(end = 10.dp)
                    )
                    InputField(
                        text = "Preço", // Preço
                        value = product.second,
                        onValueChange = { price -> onProductPriceChange(index, price) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalAlignment = Alignment.End
        ) {
            IconButton(
                onClick = { onAddProduct() },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.square_plus_solid),
                    contentDescription = "",
                    modifier = Modifier.size(45.dp),
                    tint = Color.hsl(123f, 0.66f, 0.33f, 1f)
                )
            }
            TextButton(
                onClick = onSave,
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.hsl(123f, .63f, .33f, 1f),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .width(120.dp)
                    .height(45.dp)
            ) {
                Text(
                    text = "Salvar".uppercase(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = ralewayFamily
                )
            }
        }
    }
}


@Composable
fun PromotionDropdown() {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
    ) {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = "Localized description")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text("Edit") },
                onClick = { /* Handle edit! */ },
                leadingIcon = { Icon(Icons.Outlined.Edit, contentDescription = null) }
            )
            DropdownMenuItem(
                text = { Text("Settings") },
                onClick = { /* Handle settings! */ },
                leadingIcon = { Icon(Icons.Outlined.Settings, contentDescription = null) }
            )
            DropdownMenuItem(
                text = { Text("Send Feedback") },
                onClick = { /* Handle send feedback! */ },
                leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null) },
                trailingIcon = { Text("F11", textAlign = TextAlign.Center) }
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun StorePromotionEditScreenPreview() {
    NotasocialTheme {
        StorePromotionEditScreen()
    }
}

@Composable
@Preview(showBackground = true)
fun PromotionEditTopSectionPreview() {
    NotasocialTheme {
        PromotionEditTopSection()
    }
}

@Composable
@Preview(showBackground = true)
fun PromotionFormSectionPreview() {
    NotasocialTheme {
        PromotionFormSection(
            uiState = PromotionUiState(),
            onAddProduct = {},
            onTitleChange = {},
            onDueDateChange = {},
            onStoreChange = {},
            onProductNameChange = { _, _ -> },
            onProductPriceChange = { _, _ -> },
            onSave = {}
        )
    }
}