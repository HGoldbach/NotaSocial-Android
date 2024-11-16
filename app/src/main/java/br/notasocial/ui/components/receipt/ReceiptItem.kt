package br.notasocial.ui.components.receipt

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.interFamily
import br.notasocial.ui.theme.ralewayFamily

@Composable
fun ReceiptItem(
    modifier: Modifier = Modifier
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .background(color = Color.White, shape = RoundedCornerShape(15.dp))
            .padding(12.dp)
            .clickable { showDialog = !showDialog },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.receipt),
            contentDescription = "",
            tint = Color.hsl(123f, .63f, .33f, 1f),
            modifier = modifier.size(80.dp)
        )
        Text(
            text = "Nota",
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            fontFamily = ralewayFamily,
            modifier = Modifier.padding(12.dp)
        )
        Text(
            text = "Cadastro",
            fontSize = 12.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            fontFamily = ralewayFamily,
        )
        Text(
            text = "01/05/2024",
            fontSize = 11.sp,
            color = Color.Black,
            fontWeight = FontWeight.Normal,
            fontFamily = interFamily
        )
        if(showDialog) {
            ReceiptDialog(
                onDismissRequest = {
                    showDialog = !showDialog
                },
                description = ""
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ReceiptItemPreview() {
    NotasocialTheme {
        ReceiptItem()
    }
}