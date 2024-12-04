package br.notasocial.ui.components.product

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.w3c.dom.Comment
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
    navigateToProfile: (String) -> Unit = {},
    likeReview: () -> Unit,
    commentValue: String,
    onCommentChange: (String) -> Unit,
    onSubmitComment: (String) -> Unit, // Passando o comentário
) {
    var isLiked by remember { mutableStateOf(false) }
    var isCommenting by remember { mutableStateOf(false) }

    var comments by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = modifier
    ) {
        // Exibição do review
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .size(50.dp)
                    .clickable { navigateToProfile(review.user.keycloakId) },
                color = Color.White,
                shape = CircleShape,
                border = BorderStroke(1.dp, color = Color.Black)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(review.user.photo)
                        .crossfade(true)
                        .build(),
                    error = painterResource(R.drawable.nota_social_typho),
                    placeholder = painterResource(R.drawable.loading_img),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
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
                    for (i in 1..review.rating) {
                        Icon(
                            painter = painterResource(id = R.drawable.star_solid),
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                    for (i in 1..(5 - review.rating)) {
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

        // Texto do review
        Text(
            text = review.review,
            fontSize = 12.sp,
            fontFamily = ralewayFamily,
            color = Color.Black,
            fontWeight = FontWeight.ExtraLight,
            lineHeight = 1.5.em,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .padding(start = 10.dp)
        )

        // Exibição dos comentários
        comments.forEach { comment ->
            Column(
                modifier = Modifier.padding(start = 10.dp, top = 10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { navigateToProfile(review.user.keycloakId) },
                        color = Color.White,
                        shape = CircleShape,
                        border = BorderStroke(1.dp, color = Color.Black)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.user_solid),
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier.padding(8.dp)
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
                    }
                }
                Text(
                    text = comment,
                    fontSize = 12.sp,
                    fontFamily = ralewayFamily,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraLight,
                    lineHeight = 1.5.em,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .padding(start = 10.dp)
                )
            }
        }

        // Botão para abrir a área de comentário
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .size(35.dp)
                    .clickable {
                        likeReview()
                        isLiked = !isLiked
                    },
                color = Color.White,
                shape = CircleShape,
                border = BorderStroke(1.dp, color = Color.Black)
            ) {
                Icon(
                    painter = if (isLiked) {
                        painterResource(id = R.drawable.thumbs_up_solid)
                    } else {
                        painterResource(id = R.drawable.thumbs_up_regular)
                    },
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
                modifier = Modifier
                    .padding(start = 10.dp)
                    .clickable { isCommenting = !isCommenting }
            )
        }

        // Exibição do campo de comentário
        if (isCommenting) {
            Column(
                modifier = Modifier
                    .height(150.dp)
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                    .border(
                        2.dp,
                        color = Color.hsl(123f, 0.66f, 0.33f, 1f),
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                TextField(
                    value = commentValue,
                    onValueChange = { onCommentChange(it) },
                    placeholder = { Text(text = "Escreva seu comentário...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                    )
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    Button(
                        onClick = {
                            // Adiciona o novo comentário diretamente na lista
                            comments = comments + commentValue
                            onSubmitComment(commentValue) // Limpa o campo de comentário
                            isCommenting = false // Fecha o campo de comentário
                        },
                        modifier = Modifier
                            .width(120.dp)
                            .padding(top = 10.dp, end = 10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.hsl(123f, 0.66f, 0.33f, 1f),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(
                            text = "Comentar"
                        )
                    }
                }
            }
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
            review = mockReview,
            likeReview = {},
            commentValue = "",
            onCommentChange = {},
            onSubmitComment = {},
        )
    }
}