package br.notasocial.ui.view.store

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.components.store.InputField
import br.notasocial.ui.components.store.MaskVisualTransformation
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.viewmodel.customer.userprofile.UserProfileViewModel
import br.notasocial.ui.viewmodel.store.AddressUiState
import br.notasocial.ui.viewmodel.store.AddressViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest

object StoreAddressEditDestination : NavigationDestination {
    override val route = "address_edit"
    override val title = "address_edit"
}

@Composable
fun StoreAddressEditScreen(
    viewModel: AddressViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateToAddresses: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is AddressViewModel.UiEvent.AddressSuccess -> {
                    Toast.makeText(
                        context,
                        "Endereço cadastrada com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()
                        navigateToAddresses()
                }

                is AddressViewModel.UiEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }

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
            AddressEditTopSection()
            AddressFormSection(
                onStreetChange = viewModel::onStreetChange,
                onNumberChange = viewModel::onNumberChange,
                onComplementChange = viewModel::onComplementChange,
                onNeighborhoodChange = viewModel::onNeighborhoodChange,
                onCityChange = viewModel::onCityChange,
                onStateChange = viewModel::onStateChange,
                onCepChange = viewModel::onCepChange,
                onTelephoneChange = viewModel::onTelephoneChange,
                onSave = viewModel::saveAddress,
                uiState = uiState,
            )
        }
    }
}

@Composable
fun AddressEditTopSection(
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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            )
        }
        Text(
            text = "Endereço",
            fontFamily = ralewayFamily,
            fontSize = 24.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@Composable
fun AddressFormSection(
    uiState: AddressUiState,
    onStreetChange: (String) -> Unit,
    onNumberChange: (String) -> Unit,
    onComplementChange: (String) -> Unit,
    onNeighborhoodChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
    onStateChange: (String) -> Unit,
    onCepChange: (String) -> Unit,
    onTelephoneChange: (String) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        InputField(
            text = "Rua",
            value = uiState.street,
            onValueChange = onStreetChange,
            modifier = Modifier.padding(top = 30.dp)
        )
        Row(
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            InputField(
                text = "Número",
                value = uiState.number,
                onValueChange = onNumberChange,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp)
            )
            InputField(
                text = "Complemento",
                value = uiState.complement,
                onValueChange = onComplementChange,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.weight(2f)
            )
        }
        Row(
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            InputField(
                text = "Bairro",
                value = uiState.neighborhood,
                onValueChange = onNeighborhoodChange,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp)
            )
            InputField(
                text = "Cidade",
                value = uiState.city,
                onValueChange = onCityChange,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            InputField(
                text = "Estado",
                value = uiState.state,
                onValueChange = onStateChange,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp)
            )
            InputField(
                text = "Cep",
                value = uiState.cep,
                onValueChange = {
                    if (it.length <= cepMask.INPUT_LENGTH) onCepChange(it)
                },
                visualTransformation = MaskVisualTransformation(cepMask.MASK),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }
        InputField(
            text = "Telefone",
            value = uiState.telephone,
            onValueChange = {
                if (it.length <= phoneMask.INPUT_LENGTH) onTelephoneChange(it)
            },
            visualTransformation = MaskVisualTransformation(phoneMask.MASK),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalAlignment = Alignment.End
        ) {
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
                    text = "Cadastrar".uppercase(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = ralewayFamily
                )
            }
        }
    }
}

object phoneMask {
    const val MASK = "(##) #####-####"
    const val INPUT_LENGTH = 11 // Equals to "#####-###".count { it == '#' }
}

object cepMask {
    const val MASK = "#####-###"
    const val INPUT_LENGTH = 8 // Equals to "#####-###".count { it == '#' }
}

@Composable
@Preview(showBackground = true)
fun StoreAddressEditScreenPreview() {
    NotasocialTheme {
        StoreAddressEditScreen(
            navigateToAddresses = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AddressEditTopSectionPreview() {
    NotasocialTheme {
        AddressEditTopSection()
    }
}