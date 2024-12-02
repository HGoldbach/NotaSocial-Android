package br.notasocial.ui.components.product

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.data.model.Catalog.Category
import br.notasocial.data.model.Catalog.Product
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.interFamily
import br.notasocial.ui.theme.ralewayFamily
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.util.Locale

@Composable
fun ShopListItem(
    product: Product,
    modifier: Modifier = Modifier,
    onRemoveClick: (String) -> Unit
) {
    val formattedPrice = String.format(Locale("pt", "BR"), "R$%.2f", product.price)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
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
            modifier = Modifier.size(75.dp).padding(10.dp)
        )
//        Image(
//            painter = painterResource(id = R.drawable.loading_img),
//            contentDescription = "",
//            modifier = Modifier.size(75.dp)
//        )
        Column(
            modifier = Modifier
        ) {
            Text(
                text = "Alimentos",
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = ralewayFamily
            )
            Text(
                text = "${product.name}",
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = ralewayFamily
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier.padding(end = 15.dp),
            horizontalAlignment = Alignment.End
        ) {
            Icon(
                painter = painterResource(id = R.drawable.trash_can_solid),
                contentDescription = "",
                tint = Color.Red,
                modifier = Modifier.size(14.dp).clickable { onRemoveClick(product.id!!) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = formattedPrice,
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontFamily = interFamily,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ShopListItemPreview() {
    NotasocialTheme {
        val mockProduct = Product(
            id = "1",
            name = "Pao Forma Seven Boys",
            category = Category(1, "Padaria", ""),
            image = "",
            price = 6.69,
            code = "123456",
            storeId = "",
            unit = ""
        )
        ShopListItem(
            product = mockProduct,
            onRemoveClick = {}
        )
    }
}