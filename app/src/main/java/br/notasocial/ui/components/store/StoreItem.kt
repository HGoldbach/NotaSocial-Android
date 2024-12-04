package br.notasocial.ui.components.store

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.data.model.Store
import br.notasocial.ui.theme.ralewayFamily
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun StoreItem(
    store: Store,
    navigateToStore: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(color = Color.White, shape = RoundedCornerShape(15.dp))
            .padding(25.dp)
            .clickable { navigateToStore(store.id!!) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(store.image)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.nota_social_typho),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        )
        store.name?.lowercase()
            ?.split(" ")?.let {
                Text(
                    text = it
                        .joinToString(" ") { word ->
                            word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                        },
                    fontFamily = ralewayFamily,
                    fontSize = 12.sp,
                    lineHeight = 1.em,
                    color = Color.Black,
                    minLines = 2,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
    }
}

@Composable
@Preview(showBackground = true)
fun StoreItemPreview() {
    val mockStore = Store(
        id = "1",
        approved = false,
        cnpj = "123456789",
        email = "william.henry.harrison@example-pet-store.com",
        image = "",
        keycloakId = "1",
        name = "Carrefour"
    )
    StoreItem(
        store = mockStore,
        navigateToStore = {}
    )
}