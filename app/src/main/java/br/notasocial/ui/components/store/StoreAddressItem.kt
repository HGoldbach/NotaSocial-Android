package br.notasocial.ui.components.store

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.view.consumidor.storeprofile.Endereco

@Composable
fun StoreAddressItem(
    endereco: Endereco,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        border = BorderStroke(width = 1.dp, color = Color.hsl(128f, .52f, .47f, .2f)),
        shape = RoundedCornerShape(15.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = endereco.logo),
                contentDescription = "",
                modifier = Modifier.size(120.dp)
            )
            Text(
                text = endereco.rua,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = ralewayFamily,
                minLines = 2,
                color = Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 1.2.em,
                modifier = Modifier.width(100.dp)
            )
        }
    }
}

@Composable
@Preview
fun StoreAddressItemPreview() {
    NotasocialTheme {
        StoreAddressItem(
            endereco = Endereco(1, "Carrefour", R.drawable.carrefour_logo, "Av. Mal. Floriano Peixoto", "3031", "Rebouças", "Curitiba", "PR", "80220-000", "(11) 3004-2222")
        )
    }
}