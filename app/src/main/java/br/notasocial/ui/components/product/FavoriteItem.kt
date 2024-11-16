package br.notasocial.ui.components.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.data.model.Social.Product
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily

@Composable
fun FavoriteItem(
    product: Product,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(color = Color.White, shape = RoundedCornerShape(15.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
        ) {
            Image(
                painter = painterResource(product.image),
                contentDescription = "",
                modifier = Modifier.padding(30.dp)
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
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Text(
            text = product.name,
            fontSize = 10.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            fontFamily = ralewayFamily,
            modifier = Modifier.padding(bottom = 12.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun FavoriteItemPreview() {
    NotasocialTheme {
        FavoriteItem(
            product = Product(1, "Pao Forma Seven Boys", R.drawable.pao_forma, true, 6.69)
        )
    }
}