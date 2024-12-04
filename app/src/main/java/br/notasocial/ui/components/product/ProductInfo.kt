package br.notasocial.ui.components.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.data.model.ProductDto
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.interFamily
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.utils.textTitleCase
import br.notasocial.ui.utils.formatPrice
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ProductInfo(
    reviewsTotal: Int,
    reviewRating: Double,
    product: ProductDto,
    modifier: Modifier = Modifier,
    branchStoreName: String?
) {
    Row(
        modifier = modifier
            .height(120.dp)
    ) {
        Column(
            modifier = modifier
                .weight(1f)
                .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                .padding(12.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(product.image)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.nota_social_typho),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxHeight()
            )
        }
        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
            ) {
                Text(
                    text = textTitleCase(product.name),
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontFamily = ralewayFamily
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in 1..reviewRating.toInt()) {
                        Icon(
                            painter = painterResource(id = R.drawable.star_solid),
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    for (i in 1..(5 - reviewRating.toInt())) {
                        Icon(
                            painter = painterResource(id = R.drawable.star_regular),
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Text(
                        text = "${reviewsTotal} avaliações",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontFamily = ralewayFamily,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.padding(start = 5.dp, bottom = 5.dp)
                    )
                }
            }
            Divider(
                modifier = Modifier.height(3.dp),
                color = Color.White
            )
            Column {
                Row() {
                    Text(
                        text = "Menor preço encontrado: ",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 10.sp,
                        color = Color.Black,
                        fontFamily = ralewayFamily
                    )
                    Text(
                        text = formatPrice(product.price),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 10.sp,
                        fontFamily = interFamily,
                        color = Color.hsl(123f, 0.66f, 0.33f, 1f)
                    )
                }
                Row() {
                    Text(
                        text = "Estabelecimento: ",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 10.sp,
                        color = Color.Black,
                        fontFamily = ralewayFamily
                    )
                    Text(
                        text = textTitleCase(branchStoreName ?: ""),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 10.sp,
                        maxLines = 1,
                        fontFamily = ralewayFamily,
                        color = Color.hsl(123f, 0.66f, 0.33f, 1f)
                    )
                }
            }
            Divider(
                modifier = Modifier.height(3.dp),
                color = Color.White
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProductInfoPreview() {
    NotasocialTheme {

        val mockProduct = ProductDto(
            id = "1",
            name = "Pao Forma Seven Boys",
            code = "",
            image = "",
            price = 10.0,
            storeId = "",
            unit = ""
        )
        ProductInfo(
            reviewsTotal = 3,
            reviewRating = 4.0,
            product = mockProduct,
            branchStoreName = ""
        )
    }
}