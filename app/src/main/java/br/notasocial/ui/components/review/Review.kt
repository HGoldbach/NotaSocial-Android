package br.notasocial.ui.components.review

import androidx.compose.foundation.BorderStroke
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
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily

@Composable
fun Review(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(50.dp),
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
                    text = "JOÃO MARIA",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    fontFamily = ralewayFamily
                )
                Text(
                    text = "07 Maio 2024",
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    fontSize = 11.sp,
                    fontFamily = ralewayFamily
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.star_solid),
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier.size(12.dp)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.star_solid),
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier.size(12.dp)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.star_solid),
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier.size(12.dp)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.star_solid),
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier.size(12.dp)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.star_regular),
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }
        Text(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis  finibus tellus id feugiat. Cras rhoncus nibh dui, quis vestibulum massa  vulputate non. Suspendisse non pulvinar enim. Suspendisse eleifend sit  amet nibh id ultricies. Etiam justo libero, lobortis a efficitur a,  mollis sit amet eros. Maecenas vitae quam aliquam, consectetur lacus ut,  efficitur nunc. Sed luctus vitae lorem sed convallis. Maecenas  convallis nec eros tincidunt sagittis.",
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
fun ReviewPreview() {
    NotasocialTheme {
        Review()
    }
}