package br.notasocial.ui.components.product

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.notasocial.R
import br.notasocial.data.model.Catalog.Product
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun SimilarProduct(
    product: Product,
    modifier: Modifier = Modifier,
    navigateToProduct: (String) -> Unit
) {
    Column(
        modifier = modifier
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
            .padding(10.dp)
            .size(50.dp)
            .clickable { navigateToProduct(product.id!!) }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(product.image)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

