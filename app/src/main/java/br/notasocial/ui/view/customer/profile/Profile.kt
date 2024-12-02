package br.notasocial.ui.view.consumidor.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.data.model.Social.Product
import br.notasocial.data.model.Social.Review
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.NotaSocialBottomAppBar
import br.notasocial.ui.NotaSocialTopAppBar
import br.notasocial.ui.components.profile.FollowUserItem
import br.notasocial.ui.components.profile.MenuCardItem
import br.notasocial.ui.components.profile.ProfileInfo
import br.notasocial.ui.components.profile.ReviewItem
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.viewmodel.consumidor.profile.ProfileViewModel

object ProfileDestination : NavigationDestination {
    override val route = "profile"
    override val title = "Perfil"
}

enum class MenuItem {
    AVALIACOES,
    SEGUINDO,
    SEGUIDORES
}

@Composable
fun ProfileScreen(
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
    viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var selectedMenuItem by remember { mutableStateOf(MenuItem.AVALIACOES) }
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
                .padding(it)
                .verticalScroll(rememberScrollState())
                .background(Color.hsl(0f, 0f, .97f, 1f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                ProfileTopSection()
                ProfileMenuSection(
                    selectedMenuItem = selectedMenuItem,
                    onMenuItemClick = { selectedMenuItem = it },
                    modifier = Modifier.padding(vertical = 20.dp)
                )
                when (selectedMenuItem) {
                    MenuItem.AVALIACOES -> ReviewSection(reviews = viewModel.reviews)
                    MenuItem.SEGUINDO -> FollowSection(text = stringResource(id = R.string.menu_item_following), perfis = 10)
                    MenuItem.SEGUIDORES -> FollowSection(text = stringResource(id = R.string.menu_item_followers), perfis = 10)
                }
            }
        }
    }
}


@Composable
fun ProfileTopSection(
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
        ProfileInfo()
    }

}

@Composable
fun ProfileMenuSection(
    selectedMenuItem: MenuItem,
    onMenuItemClick: (MenuItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        MenuCardItem(
            text = stringResource(id = R.string.menu_card_item_reviews),
            quantity = 50,
            modifier = Modifier
                .weight(1f)
                .clickable { onMenuItemClick(MenuItem.AVALIACOES) },
            isActive = selectedMenuItem == MenuItem.AVALIACOES,
        )
        Spacer(modifier = Modifier.size(15.dp))
        MenuCardItem(
            text = stringResource(id = R.string.menu_card_item_followings),
            quantity = 50,
            modifier = Modifier
                .weight(1f)
                .clickable { onMenuItemClick(MenuItem.SEGUINDO) },
            isActive = selectedMenuItem == MenuItem.SEGUINDO
        )
        Spacer(modifier = Modifier.size(15.dp))
        MenuCardItem(
            text = stringResource(id = R.string.menu_card_item_followers),
            quantity = 50,
            modifier = Modifier
                .weight(1f)
                .clickable { onMenuItemClick(MenuItem.SEGUIDORES) },
            isActive = selectedMenuItem == MenuItem.SEGUIDORES
        )
    }
}

@Composable
fun ReviewSection(
    reviews: List<Review>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
    ) {
        Text(
            text = stringResource(id = R.string.reviews_title),
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
        reviews.forEach { review ->
            ReviewItem(
                review = review
            )
            Divider(
                thickness = 5.dp,
                color = Color.hsl(0f, 0f, .97f, 1f),
                modifier = Modifier.width(80.dp)
            )
        }
    }
}

@Composable
fun FollowSection(
    text: String,
    perfis: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
    ) {
        Text(
            text = text,
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth().height(500.dp),
        ) {
            items(perfis) {
                FollowUserItem(
                    navigateToProfile = {},
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FollowSectionPreview() {
    NotasocialTheme {
        FollowSection(
            text = "Seguindo",
            perfis = 10
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileTopSectionPreview() {
    NotasocialTheme {
        ProfileTopSection()
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileMenuSectionPreview() {
    NotasocialTheme {
        ProfileMenuSection(
            selectedMenuItem = MenuItem.AVALIACOES,
            onMenuItemClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewSectionPreview() {
    NotasocialTheme {
        val mockReviews = listOf(
            Review(
                id = 1,
                product = Product("1", "Pão Forma Seven Boys", R.drawable.pao_forma, null, emptyList(), false, 10.0),
                stars = 2,
                comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis"
            ),
            Review(
                id = 2,
                product = Product("2", "Mamao Formosa", R.drawable.mamao_formosa, null, emptyList(),false, 10.0),
                stars = 3,
                comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis"
            ),
            Review(
                id = 3,
                product = Product("2", "Arroz Buriti", R.drawable.arroz_buriti, null, emptyList(),false, 10.0),
                stars = 1,
                comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis"
            )
        )
        ReviewSection(
            reviews = mockReviews
        )
    }
}