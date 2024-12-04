package br.notasocial.ui.components.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.data.model.Catalog.Category
import br.notasocial.ui.components.product.ProductReviewDialog
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun CategoryItem(
    text: String,
    navigateToSearchCategory: (Int, String, String) -> Unit,
    modifier: Modifier = Modifier,
    category: Category
) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Surface(
            modifier = modifier
                .size(100.dp)
                .padding(10.dp),
            shape = CircleShape,
            color = Color.White
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(category.image)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = "",
                contentScale = ContentScale.Inside,
                modifier = Modifier.size(20.dp).padding(10.dp).clickable { showDialog = !showDialog }
            )
        }
        Text(
            text = text,
            color = Color.Black,
            fontFamily = ralewayFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp
        )
        if (showDialog) {
            CategoryDialog(
                onDismissRequest = {
                    showDialog = !showDialog
                },
                category = category,
                navigateToSearchCategory = navigateToSearchCategory
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CategoryItemPreview() {
    NotasocialTheme {
        CategoryItem(
            text = "Alimentos",
            category = Category(
                id = 1,
                name = "Alimentos",
                description = "Alimentos",
                image = ""
            ),
            navigateToSearchCategory = { _,_, _ -> }
        )
    }
}