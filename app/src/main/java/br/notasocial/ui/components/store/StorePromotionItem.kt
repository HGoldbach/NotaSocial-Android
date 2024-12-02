package br.notasocial.ui.components.store

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.data.model.StoreDb.AddressDb
import br.notasocial.data.model.StoreDb.PromotionDb
import br.notasocial.ui.theme.interFamily
import br.notasocial.ui.theme.ralewayFamily

@Composable
fun StorePromotionItem(
    navigateToPromotion: (Int, String) -> Unit,
    modifier: Modifier = Modifier,
    storeName: String,
    promotion: PromotionDb
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(15.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .width(80.dp)
                    .height(20.dp),
                color = Color.hsl(123f, .63f, .33f, 1f),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.promotion_tag),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = ralewayFamily,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 1.dp)
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
                text = "Valido até ${promotion.validity} ",
                fontSize = 10.sp,
                color = Color.Black,
                fontWeight = FontWeight.Light,
                fontFamily = interFamily,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
        Text(
            text = "Promoção de Produtos",
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontFamily = ralewayFamily,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = "Diversos produtos em promoção na loja\n${promotion.establishment}",
            fontSize = 11.sp,
            fontWeight = FontWeight.Light,
            color = Color.Black,
            fontFamily = ralewayFamily,
            lineHeight = 1.2.em,
        )
        Text(
            text = "Ver Produtos",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = ralewayFamily,
            color = Color.hsl(123f, .63f, .33f, 1f),
            modifier = Modifier
                .padding(top = 8.dp)
                .clickable { navigateToPromotion(promotion.id, storeName) }
        )
    }
}
