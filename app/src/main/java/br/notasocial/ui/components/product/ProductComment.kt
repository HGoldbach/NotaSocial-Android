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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.data.model.Social.CommentResponseItem
import br.notasocial.data.model.Social.Review
import br.notasocial.ui.theme.interFamily
import br.notasocial.ui.theme.ralewayFamily
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun ProductComment(
    comment: CommentResponseItem,
    modifier: Modifier = Modifier,
//    navigateToProfile: (String) -> Unit = {},
//    likeReview: () -> Unit,
//    commentValue: String,
//    onCommentChange: (String) -> Unit,
//    onSubmitComment: (String) -> Unit,
) {
    var isCommenting by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .size(50.dp)
                    .clickable {  },
                color = Color.White,
                shape = CircleShape,
                border = BorderStroke(1.dp, color = Color.Black)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.user_solid),
                    contentDescription = "",
                    tint = Color.Black,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Column(
                modifier = Modifier.padding(start = 5.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "${comment.user.firstName} ${comment.user.lastName}".uppercase(),
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    fontFamily = ralewayFamily
                )
                Text(
                    text = formatDateTime(comment.createdAt),
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.sp,
                    fontFamily = interFamily
                )
            }
        }
        Text(
            text = comment.text,
            fontSize = 12.sp,
            fontFamily = ralewayFamily,
            color = Color.Black,
            fontWeight = FontWeight.ExtraLight,
            lineHeight = 1.5.em,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .padding(start = 10.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
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
//                TextField(
//                    value = commentValue,
//                    onValueChange = { onCommentChange(it) },
//                    placeholder = { Text(text = "Escreva seu comentário...") },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(10.dp),
//                    colors = TextFieldDefaults.colors(
//                        focusedTextColor = Color.Black,
//                        unfocusedTextColor = Color.Black,
//                        focusedContainerColor = Color.White,
//                        unfocusedContainerColor = Color.White,
//                    )
//                )
//                Column(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalAlignment = Alignment.End
//                ) {
//                    Button(
//                        onClick = {
//                            onSubmitComment(review.id)
//                            isCommenting = !isCommenting
//                        },
//                        modifier = Modifier
//                            .width(120.dp)
//                            .padding(top = 10.dp, end = 10.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = Color.hsl(123f, 0.66f, 0.33f, 1f),
//                            contentColor = Color.White
//                        ),
//                        shape = RoundedCornerShape(5.dp)
//                    ) {
//                        Text(
//                            text = "Comentar"
//                        )
//                    }
//                }
            }
        }
    }
}