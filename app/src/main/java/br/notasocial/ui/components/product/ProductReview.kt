package br.notasocial.ui.components.product

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.data.model.Social.Review
import br.notasocial.data.model.User.UserResponse
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.interFamily
import br.notasocial.ui.theme.ralewayFamily
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatDateTime(dateTimeString: String): String {
    val dateTime = LocalDateTime.parse(dateTimeString)
    return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH))
}

@Composable
fun ProductReview(
    review: Review,
    modifier: Modifier = Modifier,
    navigateToProfile: (String) -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(50.dp).clickable { navigateToProfile(review.user.keycloakId) },
                color = Color.White,
                shape = CircleShape,
                border = BorderStroke(1.dp, color = Color.Black)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.user_solid),
                    contentDescription = "",
                    modifier = Modifier.padding(12.dp)
                )
            }
            Column(
                modifier = Modifier.padding(start = 5.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "${review.user.firstName} ${review.user.lastName}".uppercase(),
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    fontFamily = ralewayFamily
                )
                Text(
                    text = formatDateTime(review.createdAt),
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.sp,
                    fontFamily = interFamily
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for(i in 1..review.rating) {
                        Icon(
                            painter = painterResource(id = R.drawable.star_solid),
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                    for(i in 1..(5 - review.rating)) {
                        Icon(
                            painter = painterResource(id = R.drawable.star_regular),
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
        }
        Text(
            text = review.review,
            fontSize = 12.sp,
            fontFamily = ralewayFamily,
            color = Color.Black,
            fontWeight = FontWeight.ExtraLight,
            lineHeight = 1.5.em,
            modifier = Modifier.padding(vertical = 10.dp).padding(start = 10.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(35.dp),
                color = Color.White,
                shape = CircleShape,
                border = BorderStroke(1.dp, color = Color.Black)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.thumbs_up_regular),
                    contentDescription = "",
                    tint = Color.Black,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Text(
                text = "Comentar",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color.Black,
                fontFamily = ralewayFamily,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProductReviewPreview() {
    NotasocialTheme {
        val mockReview = Review(
            id = "1",
            createdAt = "10/10/2023",
            rating = 4,
            review = "Produto muito bom, recomendo!",
            user = UserResponse(
                id = "1",
                firstName = "Varian",
                lastName = "Wrynn"
            ),
            product = null
        )
        ProductReview(
            review = mockReview
        )
    }
}