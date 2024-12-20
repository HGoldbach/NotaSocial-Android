package br.notasocial.ui.components.receipt

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.notasocial.R
import br.notasocial.data.model.Receipt.Receipt
import br.notasocial.ui.theme.interFamily
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.utils.textTitleCase
import br.notasocial.ui.utils.formatPrice

@Composable
fun ReceiptDialog(
    onDismissRequest: () -> Unit,
    description: String,
    modifier: Modifier = Modifier,
    receipt: Receipt
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.receipt_solid),
                        contentDescription = description,
                        tint = Color.hsl(123f, .63f, .33f, 1f),
                        modifier = Modifier.size(80.dp).padding(end = 12.dp)
                    )
                    Column(
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Text(
                            text = "Nota",
                            fontSize = 24.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontFamily = ralewayFamily
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "Cadastro",
                            fontSize = 16.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium,
                            fontFamily = ralewayFamily
                        )
                        Text(
                            text = "${receipt.scannedAt[2]}/${receipt.scannedAt[1]}/${receipt.scannedAt[0]}",
                            fontSize = 13.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Light,
                            fontFamily = interFamily
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painter = painterResource(id = R.drawable.close_xmark),
                        contentDescription = "",
                        tint = Color.Red,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { onDismissRequest() }
                    )
                }
                Text(
                    text = "Produtos",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    fontFamily = ralewayFamily,
                    modifier = Modifier.fillMaxWidth().padding(top = 25.dp, bottom = 18.dp)
                )
                LazyColumn(
                    modifier = Modifier.height(200.dp)
                ) {
                    items(items = receipt.items, key = { it.code }) { item ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = textTitleCase(item.name),
                                fontSize = 12.sp,
                                maxLines = 2,
                                color = Color.Black,
                                fontWeight = FontWeight.Normal,
                                fontFamily = ralewayFamily,
                                modifier = Modifier.width(200.dp)
                            )
                            Text(
                                text = formatPrice(item.totalValue),
                                fontSize = 12.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Normal,
                                fontFamily = interFamily
                            )
                        }
                        Divider(
                            thickness = 1.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp, bottom = 10.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Total: ",
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontFamily = ralewayFamily
                        )
                        Text(
                            text = formatPrice(receipt.totalValue),
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = interFamily
                        )
                    }
                    TextButton(
                        onClick = {},
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = Color.hsl(123f, .63f, .33f, 1f),
                            contentColor = Color.White
                        ),
                    ) {
                        Text(
                            text = "Adicionar a Lista",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = ralewayFamily,
                        )
                    }
                }
            }
        }
    }
}

//@Composable
//@Preview(showBackground = true)
//fun ReceiptDialogPreview() {
//    NotasocialTheme {
//        ReceiptDialog(
//            onDismissRequest = {},
//            description = "",
//            receipt = receipt
//        )
//    }
//}
