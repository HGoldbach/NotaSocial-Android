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
import br.notasocial.ui.viewmodel.customer.userprofile.FollowingViewModel

object FollowingDestination : NavigationDestination {
    override val route = "userprofile_following"
    override val title = "Seguindo"
}

@Composable
fun FollowingScreen(
    navigateToProfile: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FollowingViewModel = viewModel(factory = AppViewModelProvider.Factory)
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
            FollowingTopSection()
            Spacer(modifier = Modifier.padding(10.dp))
            FollowingGrid(
                navigateToProfile = navigateToProfile,
                users = viewModel.users
            )
        }
    }
}

@Composable
fun FollowingTopSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.following_title),
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            fontFamily = ralewayFamily,
            modifier = Modifier.padding(vertical = 15.dp)
        )
    }
}

@Composable
fun FollowingGrid(
    modifier: Modifier = Modifier,
    users: List<UserResponse>,
    navigateToProfile: (String) -> Unit
) {
    if (users.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Nenhum usuário encontrado",
                color = Color.Black,
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
            items(items = users, key = { it.id }) { user ->
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
fun FollowingScreenPreview() {
    NotasocialTheme {
        FollowingScreen(
            {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FollowingTopSectionPreview() {
    NotasocialTheme {
        FollowingTopSection()
    }
}

@Preview(showBackground = true)
@Composable
fun FollowingGridPreview() {
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
    NotasocialTheme {
        FollowingGrid(users = mockUsers, navigateToProfile = {})
    }
}

