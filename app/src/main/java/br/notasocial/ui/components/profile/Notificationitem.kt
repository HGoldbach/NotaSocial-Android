package br.notasocial.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.data.model.Social.Notification
import br.notasocial.data.model.Social.NotificationType
import br.notasocial.ui.theme.interFamily
import br.notasocial.ui.theme.ralewayFamily

@Composable
fun NotificationItem(
    notification: Notification,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .size(35.dp),
                shape = RoundedCornerShape(5.dp),
                color = Color.hsl(0f, 0f, .85f, 1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.close_xmark),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.padding(5.dp)
                )
            }
            Column(
                modifier = Modifier
                    .height(35.dp)
                    .width(150.dp)
                    .padding(start = 10.dp)
                    .background(
                        color = when (notification.type) {
                            NotificationType.ALTERACAO_PRECO -> Color.hsl(123f, .63f, .33f, 1f)
                            NotificationType.SEGUIDOR -> Color.hsl(184f, .25f, .25f, 1f)
                            NotificationType.COMENTARIO -> Color.hsl(0f, 0f, .85f, 1f)
                        },
                        shape = RoundedCornerShape(5.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text =  when (notification.type) {
                        NotificationType.ALTERACAO_PRECO -> stringResource(id = R.string.notification_flag_price)
                        NotificationType.SEGUIDOR -> stringResource(id = R.string.notification_flag_follow)
                        NotificationType.COMENTARIO -> stringResource(id = R.string.notification_flag_comment)
                    },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = ralewayFamily,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.clock_solid),
                contentDescription = "",
                tint = Color.hsl(0f, 0f, .85f, 1f),
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = notification.date,
                fontSize = 10.sp,
                color = Color.Black,
                fontWeight = FontWeight.Light,
                fontFamily = interFamily,
                modifier = Modifier.padding(start = 7.dp)
            )
        }
        Text(
            text = notification.title,
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontFamily = ralewayFamily,
            modifier = Modifier.padding(top = 18.dp, bottom = 10.dp)
        )
        Text(
            text = notification.description,
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Light,
            fontFamily = ralewayFamily,
            lineHeight = 1.5.em
        )
        Text(
            text = when(notification.type) {
                NotificationType.ALTERACAO_PRECO -> stringResource(id = R.string.notification_see_product)
                NotificationType.SEGUIDOR -> stringResource(id = R.string.notification_see_follow)
                NotificationType.COMENTARIO -> stringResource(id = R.string.notification_see_comment)
            },
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = ralewayFamily,
            color = Color.hsl(123f, .63f, .33f, 1f),
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}