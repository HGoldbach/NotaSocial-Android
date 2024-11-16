package br.notasocial.ui.components.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.data.model.Category
import br.notasocial.data.model.Social.Product
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.interFamily
import br.notasocial.ui.theme.ralewayFamily

@Composable
fun ShopListItem(
    product: Product,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = product.image),
            contentDescription = "",
            modifier = Modifier.size(75.dp)
        )
        Column(
            modifier = Modifier
        ) {
            Text(
                text = "${product.category?.name?.uppercase()}",
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = ralewayFamily
            )
            Text(
                text = product.name,
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = ralewayFamily
            )
            Row {
                Text(
                    text = "Estabelecimento: ",
                    fontSize = 10.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    fontFamily = ralewayFamily
                )
                Text(
                    text = "Carrefour",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = ralewayFamily,
                    color = Color.hsl(123f, .63f, .33f, 1f),
                    textDecoration = TextDecoration.Underline
                )
            }
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
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "R$${product.price}",
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
            id = 1,
            name = "Pao Forma Seven Boys",
            category = Category(1, "Padaria", ""),
            image = R.drawable.pao_forma,
            isFavorite = true,
            price = 6.69,
        )
        ShopListItem(
            product = mockProduct
        )
    }
}