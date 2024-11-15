package br.notasocial.ui.components.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.data.model.Social.Product
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.interFamily
import br.notasocial.ui.theme.ralewayFamily

@Composable
fun ProductItem(
    product: Product,
    modifier: Modifier = Modifier
) {
    var isFavorite by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
            .width(120.dp),
        horizontalAlignment = Alignment.End
    ) {
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
                .size(18.dp)
                .clickable { isFavorite = !isFavorite }
        )
        Image(
            painter = painterResource(product.image),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = product.name,
            fontFamily = ralewayFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
            lineHeight = 1.em
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "R$${product.price}",
                fontFamily = interFamily,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
            Surface(
                modifier = Modifier
                    .size(30.dp),
                color = Color.hsl(128f, .52f, .47f, .2f),
                shape = RoundedCornerShape(5.dp)
            ) {
                IconButton(
                    onClick = {},
                ) {
                    Icon(
                        painter = painterResource(R.drawable.cart_shopping_solid),
                        contentDescription = "",
                        tint = Color.hsl(123f, .63f, .33f, 1f),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProductItemPreview() {
    NotasocialTheme {
        ProductItem(
            Product(1, "Pao Forma Seven Boys", R.drawable.pao_forma, true, 6.69)
        )
    }
}