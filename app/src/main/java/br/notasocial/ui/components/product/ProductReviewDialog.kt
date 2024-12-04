package br.notasocial.ui.components.product

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.notasocial.R
import br.notasocial.data.model.ProductDto
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.utils.textTitleCase

@Composable
fun ProductReviewDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    product: ProductDto,
    onRatingChange: (String) -> Unit,
    onReviewTextChange: (String) -> Unit,
    reviewText: String,
    reviewRating: String,
    onSaveReview: () -> Unit,
) {
    val radioOptions = listOf("1", "2", "3", "4", "5")
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
                    Column(
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Text(
                            text = textTitleCase(product.name),
                            fontSize = 12.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = ralewayFamily,
                            lineHeight = 1.em,
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painter = painterResource(id = R.drawable.close_xmark),
                        contentDescription = "",
                        tint = Color.Red,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onDismissRequest() }
                    )

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Nota:",
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium,
                        fontFamily = ralewayFamily,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    radioOptions.forEach { text ->
                        Row(
                            modifier = Modifier
                                .selectable(
                                    selected = (text == reviewRating),
                                    onClick = {
                                        onRatingChange(text)
                                    }
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            RadioButton(
                                selected = (text == reviewRating),
                                onClick = { onRatingChange(text) },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color.hsl(123f, 0.66f, 0.33f, 1f),
                                    unselectedColor = Color.hsl(123f, 0.66f, 0.33f, 1f)
                                ),
                                modifier = Modifier.size(10.dp)
                            )
                            Text(
                                text = text,
                                color = Color.Black,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Text(
                        text = "Avaliação",
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium,
                        fontFamily = ralewayFamily,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    OutlinedTextField(
                        value = reviewText,
                        onValueChange = { onReviewTextChange(it) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.hsl(360f, 0.0f, 0.97f, 1f),
                            unfocusedContainerColor = Color.hsl(360f, 0.0f, 0.97f, 1f),
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        textStyle = TextStyle(fontSize = 12.sp)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Button(
                        onClick = {
                            onSaveReview()
                            onDismissRequest()
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.hsl(123f, 0.66f, 0.33f, 1f),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Avaliar"
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ReceiptDialogPreview() {
    NotasocialTheme {
        ProductReviewDialog(
            onDismissRequest = {},
            product = ProductDto(
                id = "1",
                category = null,
                name = "Pao Forma Seven Boys",
                image = "",
                price = 2.21,
                code = "",
                storeId = "",
                unit = ""
            ),
            onRatingChange = {},
            onReviewTextChange = {},
            reviewText = "",
            reviewRating = "",
            onSaveReview = {},
        )
    }
}
