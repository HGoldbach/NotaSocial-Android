package br.notasocial.ui.view.store

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.viewmodel.store.StoreHomeViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest

object StoreHomeDestination : NavigationDestination {
    override val route = "store_home"
    override val title = "Perfil"
}

@Composable
fun StoreHomeScreen(
    navigateToAddresses: () -> Unit,
    navigateToPromotions: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StoreHomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.hsl(0f, 0f, .97f, 1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            StoreHomeTopSection(
                storeName = viewModel.loggedStore.name
            )
            Spacer(modifier = Modifier.height(30.dp))
            StoreHomeMenuSection(
                navigateToPromocoes = navigateToPromotions,
                navigateToEnderecos = navigateToAddresses
            )
        }
    }
}

@Composable
fun StoreHomeTopSection(
    modifier: Modifier = Modifier,
    storeName: String?
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
            text = if (storeName != null) {
                "$storeName".uppercase()
            } else {
                ""
            },
            fontFamily = ralewayFamily,
            fontSize = 24.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@Composable
fun StoreHomeMenuSection(
    navigateToPromocoes: () -> Unit,
    navigateToEnderecos: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        PerfilMenuItem(
            text = "Promocoes",
            navigate = navigateToPromocoes,
        )
        Spacer(modifier = Modifier.size(8.dp))
        PerfilMenuItem(
            text = "Endereços",
            navigate = navigateToEnderecos
        )
    }
}

@Composable
fun PerfilMenuItem(
    text: String,
    navigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 20.dp)
            .clickable { navigate() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontFamily = ralewayFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Icon(
            painter = painterResource(id = R.drawable.right_arrow),
            contentDescription = "",
            tint = Color.Black,
            modifier = Modifier
                .size(20.dp)
                .padding(top = 5.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PerfilPreview() {
    NotasocialTheme {
        StoreHomeScreen(
            {}, {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilTopSectionPreview() {
    NotasocialTheme {
        StoreHomeTopSection(storeName = "Carrefour")
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilMenuSectionPreview() {
    NotasocialTheme {
        StoreHomeMenuSection({}, {})
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilMenuItemPreview() {
    NotasocialTheme {
        PerfilMenuItem(
            text = "Promocoes",
            navigate = {}
        )
    }
}