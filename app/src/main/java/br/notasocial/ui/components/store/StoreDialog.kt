package br.notasocial.ui.components.store

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.notasocial.R
import br.notasocial.data.model.StoreDb.AddressDb
import br.notasocial.ui.theme.interFamily
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.utils.formatPhoneNumber
import br.notasocial.ui.utils.formatZipCode
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun StoreDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    address: AddressDb,
    onRemove: (Int) -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
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
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data("https://static.vecteezy.com/ti/vetor-gratis/p1/5766127-supermercado-loja-logo-vetor.jpg")
                            .crossfade(true)
                            .build(),
                        error = painterResource(R.drawable.ic_broken_image),
                        placeholder = painterResource(R.drawable.loading_img),
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(120.dp).weight(2f)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.close_xmark),
                        contentDescription = "",
                        tint = Color.Red,
                        modifier = Modifier
                            .size(32.dp)
                            .weight(1f)
                            .padding(start = 30.dp)
                            .clickable { onDismissRequest() }
                    )
                }
                Text(
                    text = "Filial",
                    fontSize = 32.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontFamily = ralewayFamily
                )
                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Endereço",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontFamily = ralewayFamily
                    )
                    Text(
                        text = "${address.street}, ${address.number}",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium,
                        fontFamily = ralewayFamily
                    )
                    Text(
                        text =  "${address.district}, ${address.city} - ${address.state}",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium,
                        fontFamily = ralewayFamily
                    )
                    Text(
                        text = formatZipCode(address.zipCode),
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = interFamily
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Telefone",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontFamily = ralewayFamily
                    )
                    Text(
                        text = formatPhoneNumber(address.phone),
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium,
                        fontFamily = interFamily
                    )
                }
            }
            Row(
                modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.hsl(123f, .63f, .33f, 1f),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.padding(end = 7.dp)
                ) {
                    Text(
                        text = "Editar",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = ralewayFamily
                    )
                }
                Button(
                    onClick = { onRemove(address.id) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.hsl(0f, 1f, .5f, 1f),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.padding(start = 7.dp)
                ) {
                    Text(
                        text = "Excluir",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = ralewayFamily
                    )
                }
            }
        }
    }
}

