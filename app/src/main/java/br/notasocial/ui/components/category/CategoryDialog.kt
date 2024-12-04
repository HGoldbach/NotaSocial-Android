package br.notasocial.ui.components.category

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.notasocial.R
import br.notasocial.data.model.Catalog.Category
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun CategoryDialog(
    onDismissRequest: () -> Unit,
    navigateToSearchCategory: (Int, String, String) -> Unit,
    modifier: Modifier = Modifier,
    category: Category,
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
                            .data(category.image)
                            .crossfade(true)
                            .build(),
                        error = painterResource(R.drawable.ic_broken_image),
                        placeholder = painterResource(R.drawable.loading_img),
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(120.dp)
                            .weight(2f)
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
                    text = category.name,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontFamily = ralewayFamily
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = category.description,
                        fontSize = 14.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        lineHeight = 1.em,
                        fontWeight = FontWeight.Medium,
                        fontFamily = ralewayFamily,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Button(
                        onClick = {
                            navigateToSearchCategory(category.id, category.name, category.image!!)
                            onDismissRequest()
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.hsl(123f, .63f, .33f, 1f),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Buscar Produtos"
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryDialogPreview() {
    NotasocialTheme {
        CategoryDialog(
            category = Category(
                id = 1,
                image = "",
                description = "Descrição",
                name = "Padaria e Confeitaria"
            ),
            onDismissRequest = {},
            navigateToSearchCategory = { _,_, _ -> }
        )
    }
}