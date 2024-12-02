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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.data.model.User.UserResponse
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.components.profile.FollowUserItem
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.view.customer.searchproduct.FilterSearch
import br.notasocial.ui.view.customer.searchproduct.SearchBar
import br.notasocial.ui.viewmodel.customer.userprofile.FollowersViewModel

object FollowersDestination : NavigationDestination {
    override val route = "userprofile_followers"
    override val title = "Seguidores"
}

@Composable
fun FollowersScreen(
    navigateToProfile: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FollowersViewModel = viewModel(factory = AppViewModelProvider.Factory)
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
            FollowersTopSection()
            Spacer(modifier = Modifier.padding(10.dp))
            FollowesGrid(
                followers = viewModel.user,
                navigateToProfile = navigateToProfile
            )
        }
    }
}

@Composable
fun FollowersTopSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.followers_title),
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            fontFamily = ralewayFamily,
            modifier = Modifier.padding(vertical = 15.dp)
        )
        Row {
            SearchBar(
                placeholderText = stringResource(id = R.string.followers_input_placeholder),
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
fun FollowesGrid(
    navigateToProfile: (String) -> Unit,
    modifier: Modifier = Modifier,
    followers: List<UserResponse>
) {
    if (followers.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Nenhum usuário encontrado",
                fontSize = 14.sp,
                fontFamily = ralewayFamily,
                fontWeight = FontWeight.Medium
            )
        }
    } else {
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(3),
        ) {
            items(items = followers, key = { it.id }) { user ->
                FollowUserItem(
                    navigateToProfile = navigateToProfile,
                    modifier = Modifier.padding(vertical = 12.dp),
                    user = user
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun FollowersScreenPreview() {
    NotasocialTheme {
        FollowersScreen(
            {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FollowersTopSectionPreview() {
    NotasocialTheme {
        FollowersTopSection()
    }
}

@Preview(showBackground = true)
@Composable
fun SeguidoresGridPreview() {
    NotasocialTheme {
        val mockUsers = listOf(
            UserResponse(
                firstName = "João",
                lastName = "Silva",
            ),
            UserResponse(
                firstName = "João",
                lastName = "Silva",
            ),
            UserResponse(
                firstName = "João",
                lastName = "Silva",
            ),
            UserResponse(
                firstName = "João",
                lastName = "Silva",
            )
        )
        FollowesGrid(
            navigateToProfile = {},
            followers = mockUsers
        )
    }
}