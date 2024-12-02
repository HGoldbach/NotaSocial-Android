package br.notasocial.ui.components.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import br.notasocial.data.model.Social.Review
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily

@Composable
fun ReviewItem(
    review: Review,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .height(80.dp)
        ) {
            Column(
                modifier = Modifier
                    .size(80.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(15.dp))
                    .border(
                        1.dp,
                        color = Color.hsl(0f, 0f, .97f, 1f),
                        shape = RoundedCornerShape(15.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(review.product.image),
                    contentDescription = "",
                    modifier = Modifier.padding(10.dp)
                )
            }
            Column(
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(
                    text = review.product.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontFamily = ralewayFamily
                )
                Row {
                    for (i in 1..review.stars) {
                        Icon(
                            painter = painterResource(id = R.drawable.star_solid),
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    for (i in 1..(5 - review.stars)) {
                        Icon(
                            painter = painterResource(id = R.drawable.star_regular),
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }
        Text(
            text = "Avaliação:",
            fontFamily = ralewayFamily,
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 10.dp, bottom = 2.dp)
        )
        Text(
            text = review.comment,
            fontSize = 12.sp,
            fontFamily = ralewayFamily,
            color = Color.Black,
            fontWeight = FontWeight.ExtraLight,
            lineHeight = 1.em
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ReviewItemPreview() {
    NotasocialTheme {
        val review = Review(
            id = 1,
            product = Product(1, "Pão Forma Seven Boys", R.drawable.pao_forma, null, false, 10.0),
            stars = 2,
            comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis"
        )
        ReviewItem(
            review = review
        )
    }
}