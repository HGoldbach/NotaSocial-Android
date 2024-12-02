package br.notasocial.ui.components.store

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.data.model.StoreDb.AddressDb
import br.notasocial.ui.theme.ralewayFamily
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun StoreAddressItem(
    modifier: Modifier = Modifier,
    address: AddressDb
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
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data("https://static.vecteezy.com/ti/vetor-gratis/p1/5766127-supermercado-loja-logo-vetor.jpg")
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxWidth().height(80.dp)
            )
            Text(
                text = address.street,
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
