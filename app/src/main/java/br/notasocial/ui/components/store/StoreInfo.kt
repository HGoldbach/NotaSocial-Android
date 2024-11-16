package br.notasocial.ui.components.store

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily

@Composable
fun StoreInfo(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.size(140.dp),
            color = Color.White,
            shape = CircleShape
        ) {
            Image(
                painter = painterResource(id = R.drawable.carrefour_logo),
                contentDescription = "",
                modifier = Modifier.padding(30.dp)
            )
        }
        Text(
            text = title,
            fontFamily = ralewayFamily,
            fontSize = 24.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = description,
            fontFamily = ralewayFamily,
            fontSize = 12.sp,
            color = Color.Black,
            fontWeight = FontWeight.Normal,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun StoreInfoPreview() {
    NotasocialTheme {
        StoreInfo(
            title = "Carrefour".uppercase(),
            description = "Estabelecimentos: 8"
        )
    }
}

@Composable
@Preview(showBackground = true)
fun StoreInfoPreview2() {
    NotasocialTheme {
        StoreInfo(
            title = "Promoção Carrefour",
            description = "Endereço - Av. Mal. Floariano Peixoto"
        )
    }
}