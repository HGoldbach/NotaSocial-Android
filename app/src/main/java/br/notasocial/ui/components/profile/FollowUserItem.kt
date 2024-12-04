package br.notasocial.ui.components.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.data.model.User.UserResponse
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun FollowUserItem(
    navigateToProfile: (String) -> Unit,
    modifier: Modifier = Modifier,
    user: UserResponse
) {
    Column(
        modifier = modifier.clickable { navigateToProfile(user.keycloakId) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.size(100.dp),
            color = Color.White,
            shape = CircleShape
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(user.photo)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.nota_social_typho),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxHeight()
            )
        }
        Text(
            text = "${user.firstName} ${user.lastName}",
            fontSize = 14.sp,
            color = Color.Black,
            fontFamily = ralewayFamily,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 10.dp).fillMaxWidth()
        )
    }
}

@Composable
@Preview(showBackground = true)
fun FollowUserItemPreview() {
    NotasocialTheme {

        val mockUser = UserResponse(
            firstName = "João",
            lastName = "Silva",
        )
        FollowUserItem(
            navigateToProfile = {},
            user = mockUser
        )
    }
}