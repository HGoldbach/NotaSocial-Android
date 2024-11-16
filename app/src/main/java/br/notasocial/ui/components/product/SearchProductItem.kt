package br.notasocial.ui.components.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.data.model.Catalog
import br.notasocial.ui.theme.ralewayFamily

@Composable
fun SearchProductItem(
    product: Catalog.Content,
    modifier: Modifier = Modifier
) {
    var isFavorite by remember {
        mutableStateOf(false)
    }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.loading_img),
                contentDescription = "",
                modifier = Modifier.size(200.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(40.dp),
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
                        .clickable { isFavorite = !isFavorite }
                )
            }
        }
        Text(
            text = product.name!!,
            fontFamily = ralewayFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )
    }
}