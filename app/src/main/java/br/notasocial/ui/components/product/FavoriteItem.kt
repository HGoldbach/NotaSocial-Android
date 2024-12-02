package br.notasocial.ui.components.product

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.data.model.Social.Favorite
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.utils.textTitleCase
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun FavoriteItem(
    favorite: Favorite,
    navigateToProduct: (String) -> Unit,
    modifier: Modifier = Modifier,
    onRemoveFavorite: (String) -> Unit
) {
    Column(
        modifier = modifier.background(color = Color.White, shape = RoundedCornerShape(15.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(favorite.image)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .clickable { navigateToProduct(favorite.id) }
            )
            Surface(
                modifier = Modifier
                    .padding(top = 15.dp, end = 15.dp)
                    .align(Alignment.TopEnd),
                color = Color.White
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.trash_can_solid),
                    contentDescription = "",
                    tint = Color.Red,
                    modifier = Modifier.size(20.dp).clickable { onRemoveFavorite(favorite.id) }
                )
            }
        }
        Text(
            text = textTitleCase(favorite.name),
            fontSize = 12.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            minLines = 2,
            fontFamily = ralewayFamily,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun FavoriteItemPreview() {
    NotasocialTheme {
        val mockFavorite = Favorite(
            id = "1",
            name = "Pao Forma Seven Boys",
            image = ""
        )
        FavoriteItem(
            mockFavorite,
            navigateToProduct = {},
            onRemoveFavorite = {}
        )
    }
}