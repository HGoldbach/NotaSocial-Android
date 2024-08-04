package br.notasocial.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.data.model.Catalog
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.viewmodel.CatalogUiState
import br.notasocial.ui.viewmodel.CatalogViewModel
import org.w3c.dom.Text

object CatalogDestination : NavigationDestination {
    override val route = "catalog"
    override val title = "Catalog"
}

@Composable
fun CatalogScreen(
    modifier: Modifier = Modifier,
    viewModel: CatalogViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    when (val uiState: CatalogUiState = viewModel.catalogUiState) {
        is CatalogUiState.Loading -> LoadingCatalog()
        is CatalogUiState.Error -> ErrorCatalog()
        is CatalogUiState.Success -> SuccessCatalog(
            uiState.catalog
        )
    }
}

@Composable
fun SuccessCatalog(
    catalog: Catalog,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(Color.hsl(0f,0f,0.97f,1f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "PRODUTOS",
            fontWeight = FontWeight.Black,
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 20.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .background(Color.hsl(0f, 0f, 0.97f, 1f)),
            horizontalArrangement = Arrangement.Center
        ) {
            items(catalog.content!!, key = { it?.id!! }) {
                if (it != null) {
                    catalogItem(content = it, modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))
                }
            }
        }
    }
}


@Composable
fun catalogItem(
    content: Catalog.Content,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(135.dp)
            .width(180.dp)
    ) {

        Surface(
            modifier = Modifier
                .width(180.dp)
                .height(110.dp)
                .align(Alignment.Center),
            color = Color.White,
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(5.dp, Color.hsl(0f, 0f,.97f, 1f))
        ) {
            Column(
                modifier = Modifier
                    .width(180.dp)
                    .height(110.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bottle),
                    contentDescription = "",
                    modifier = Modifier.size(48.dp),
                    colorFilter = ColorFilter.tint(Color.hsl(0f, 0f, .77f, 1f))
                )

            }
        }
        Surface(
            modifier = Modifier
                .width(132.dp)
                .height(30.dp)
                .align(Alignment.TopCenter),
            color = Color.White,
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(5.dp, Color.hsl(0f, 0f,.97f, 1f))
        ) {
            Column(
                modifier = Modifier
                    .width(150.dp)
                    .height(110.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${content.name}",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.hsl(0f, 0f, .37f, 1f)
                )
            }
        }

        Surface(
            modifier = Modifier
                .width(90.dp)
                .height(30.dp)
                .align(Alignment.BottomCenter),
            color = Color.White,
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(5.dp, Color.hsl(0f, 0f,.97f, 1f))
        ) {
            Column(
                modifier = Modifier
                    .width(150.dp)
                    .height(110.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "R$${content.price}",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.hsl(0f, 0f, .37f, 1f)
                )
            }
        }
    }
}

@Composable
fun LoadingCatalog() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.hsl(0f,0f,.97f,1f)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.loading_img),
            contentDescription = "",
            modifier = Modifier.size(192.dp)
        )
    }
}

@Composable
fun ErrorCatalog() {
    Text(text = "Error")
}