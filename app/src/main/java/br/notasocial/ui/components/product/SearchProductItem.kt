package br.notasocial.ui.components.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.data.model.Catalog.Product
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.utils.textTitleCase
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun SearchProductItem(
    product: Product,
    navigateToProduct: (String) -> Unit,
    modifier: Modifier = Modifier,
    onAddToCart: (String) -> Unit,
    userRole: String,
    onFavorite: (String) -> Unit
) {
    var isFavorite by remember {
        mutableStateOf(false)
    }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = modifier.clickable { navigateToProduct(product.id!!) }
    ) {
        Box(
            modifier = Modifier.padding(10.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(product.image)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxWidth().height(150.dp).padding(20.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(40.dp).clickable { onAddToCart(product.id!!) },
                    color = Color.hsl(128f, .52f, .47f, .2f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.cart_shopping_solid),
                        contentDescription = "",
                        tint = Color.hsl(123f, .63f, .33f, 1f),
                        modifier = Modifier
                            .size(16.dp)
                            .padding(10.dp)
                    )

                }
                Icon(
                    painter = if (isFavorite) {
                        painterResource(R.drawable.heart_solid)
                    } else {
                        painterResource(R.drawable.heart_regular)
                    },
                    contentDescription = "",
                    tint = if (isFavorite) {
                        Color.hsl(0f, 1f, .44f, 1f)
                    } else {
                        Color.Gray
                    },
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onFavorite(product.id!!)
                            if(userRole == "CUSTOMER") isFavorite = !isFavorite
                        }
                )
            }
        }
        Text(
            text = textTitleCase(product.name!!),
            fontFamily = ralewayFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            minLines = 2,
            lineHeight = 1.em,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
    }
}