package br.notasocial.ui.components.store

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.data.model.Store
import br.notasocial.ui.theme.ralewayFamily
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun StoreInfo(
    title: String,
    modifier: Modifier = Modifier,
    store: Store
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
                    .data(store.image)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.nota_social_typho),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Text(
            text = if(store.name != null) "${title}" else "",
            fontFamily = ralewayFamily,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
        )
    }
}
