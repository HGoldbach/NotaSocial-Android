package br.notasocial.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.interFamily
import br.notasocial.ui.theme.ralewayFamily

@Composable
fun MenuCardItem(
    isActive: Boolean = false,
    text: String,
    quantity: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(120.dp)
            .width(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isActive) {
                Color.hsl(123f, .63f, .33f, 1f)
            } else {
                Color.White
            }
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$quantity",
                fontFamily = interFamily,
                fontSize = 32.sp,
                fontWeight = if (isActive) {
                    FontWeight.Bold
                } else {
                    FontWeight.Normal
                },
                color = if (isActive) {
                    Color.White
                } else {
                    Color.Black
                }
            )
            Text(
                text = text,
                fontFamily = ralewayFamily,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = if (isActive) {
                    Color.White
                } else {
                    Color.Black
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MenuCardItemPreview() {
    NotasocialTheme {
        MenuCardItem(
            quantity = 10,
            text = "Avaliações"
        )
    }
}

@Composable
@Preview(showBackground = true)
fun MenuCardItemActivePreview() {
    NotasocialTheme {
        MenuCardItem(
            quantity = 10,
            text = "Avaliações",
            isActive = true
        )
    }
}