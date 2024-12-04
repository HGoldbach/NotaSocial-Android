package br.notasocial.ui.view.customer.ranking

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.data.model.Social.UserRanking
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.interFamily
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.viewmodel.customer.ranking.RankingViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest

object RankingDestination : NavigationDestination {
    override val route = "ranking"
    override val title = "Ranking"
}

@Composable
fun RankingScreen(
    modifier: Modifier = Modifier,
    viewModel: RankingViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Column(
        modifier = modifier
            .background(color = Color.hsl(0f, 0f, 0.97f, 1f))
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp),
        ) {
            if (viewModel.ranking.ranking.isNotEmpty()) {
                RankingTopSection(
                    ranking = viewModel.ranking.ranking
                )
                if (viewModel.ranking.ranking.size > 3) {
                    RankingList(
                        ranking = viewModel.ranking.ranking.subList(3, viewModel.ranking.ranking.size),
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Carregando...", modifier = Modifier.align(Alignment.CenterHorizontally), color = Color.Black)
                }
            }
//            PageList()
        }
    }
}

@Composable
fun PageList(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        PageListItem(text = "1", isActive = true)
        Spacer(modifier = Modifier.width(5.dp))
        PageListItem(text = "2")
        Spacer(modifier = Modifier.width(5.dp))
        PageListItem(text = "3")
        Spacer(modifier = Modifier.width(5.dp))
        PageListItem(text = "4")
        Spacer(modifier = Modifier.width(5.dp))
        PageListItem(text = "5")
        Spacer(modifier = Modifier.width(5.dp))
        Surface(
            modifier = Modifier.size(5.dp),
            color = Color.hsl(123f, .63f, .33f, 1f)
        ) {}
        Spacer(modifier = Modifier.width(2.dp))
        Surface(
            modifier = Modifier.size(5.dp),
            color = Color.hsl(123f, .63f, .33f, 1f)
        ) {}
        Spacer(modifier = Modifier.width(2.dp))
        Surface(
            modifier = Modifier.size(5.dp),
            color = Color.hsl(123f, .63f, .33f, 1f)
        ) {}
        Spacer(modifier = Modifier.width(5.dp))
        PageListItem(text = "10")
        Spacer(modifier = Modifier.width(5.dp))
        PageListItem(text = "20")
        Spacer(modifier = Modifier.width(5.dp))
        Surface(
            modifier = Modifier
                .width(30.dp)
                .height(25.dp),
            shape = RoundedCornerShape(5.dp),
            color = Color.hsl(128f, .52f, .47f, .3f)
        ) {
            Icon(
                painter = painterResource(R.drawable.right_arrow),
                contentDescription = "",
                tint = Color.hsl(123f, .63f, .33f, 1f),
                modifier = Modifier.padding(5.dp)
            )
        }

    }
}

@Composable
fun PageListItem(
    text: String,
    modifier: Modifier = Modifier,
    isActive: Boolean = false
) {
    Surface(
        modifier = modifier
            .width(30.dp)
            .height(25.dp),
        shape = RoundedCornerShape(5.dp),
        color = if (isActive) {
            Color.hsl(123f, .63f, .33f, 1f)
        } else {
            Color.hsl(128f, .52f, .47f, .3f)
        }
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = if (isActive) {
                Color.White
            } else {
                Color.hsl(123f, .63f, .33f, 1f)
            },
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = interFamily
        )
    }
}

@Composable
fun RankingList(
    modifier: Modifier = Modifier,
    ranking: List<UserRanking>
) {
    var number: Int by remember {
        mutableStateOf(4)
    }
    Column(
        modifier = modifier
    ) {
        Row {
            Text(
                text = "Pos.",
                fontFamily = ralewayFamily,
                fontWeight = FontWeight.ExtraLight,
                fontSize = 12.sp,
                color = Color.Black,
                modifier = Modifier.width(50.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Nome",
                color = Color.Black,
                fontFamily = ralewayFamily,
                fontWeight = FontWeight.ExtraLight,
                fontSize = 12.sp,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Notas",
                color = Color.Black,
                fontFamily = ralewayFamily,
                fontWeight = FontWeight.ExtraLight,
                fontSize = 12.sp,
                modifier = Modifier.width(60.dp)
            )
            Text(
                text = "Produtos",
                color = Color.Black,
                fontFamily = ralewayFamily,
                fontWeight = FontWeight.ExtraLight,
                fontSize = 12.sp,
                modifier = Modifier.width(60.dp)
            )
        }
        ranking.forEach { ranking ->
            RankingItem(
                position = number++,
                ranking = ranking,
                modifier = Modifier.padding(vertical = 3.dp)
            )

        }
    }
}

@Composable
fun RankingItem(
    modifier: Modifier = Modifier,
    ranking: UserRanking,
    position: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${position}º",
            modifier = Modifier.width(50.dp),
            fontFamily = interFamily,
            color = Color.Black,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
        Surface(
            modifier = Modifier.size(60.dp),
            color = Color.hsl(0f, 0f, .97f, 1f),
            shape = CircleShape
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(ranking.urlImage)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.nota_social_typho),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxHeight()
            )
        }
        Text(
            text = ranking.name,
            color = Color.Black,
            fontFamily = ralewayFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 10.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "${ranking.totalReceipts}",
            modifier = Modifier.width(80.dp),
            color = Color.Black,
            fontFamily = interFamily,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = "${ranking.totalProducts}",
            modifier = Modifier.width(60.dp),
            fontFamily = interFamily,
            color = Color.Black,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun RankingTopSection(
    modifier: Modifier = Modifier,
    ranking: List<UserRanking>
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(150.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Surface(
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.TopCenter)
                    .zIndex(1f),
                color = Color.hsl(0f, 0f, .99f, 1f),
                shape = CircleShape
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(ranking[1].urlImage)
                        .crossfade(true)
                        .build(),
                    error = painterResource(R.drawable.nota_social_typho),
                    placeholder = painterResource(R.drawable.loading_img),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp)
                    )
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (ranking.size > 1) {
                    Text(
                        text = ranking[1].name,
                        fontFamily = ralewayFamily,
                        color = Color.Black
                    )
                    Row(
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.receipt_solid),
                            contentDescription = "",
                            modifier = Modifier.size(12.dp),
                            tint = Color.hsl(123f, .63f, .33f, 1f)
                        )
                        Text(
                            text = "${ranking[1].totalReceipts}",
                            modifier = Modifier.padding(start = 5.dp),
                            fontFamily = interFamily,
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 10.sp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.product_solid),
                            contentDescription = "",
                            modifier = Modifier.size(12.dp),
                            tint = Color.hsl(123f, .63f, .33f, 1f)
                        )
                        Text(
                            text = "${ranking[1].totalProducts}",
                            color = Color.Black,
                            modifier = Modifier.padding(start = 5.dp),
                            fontFamily = interFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 10.sp
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.silver_medal),
                        contentDescription = "",
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }

        Box(
            modifier = Modifier.weight(1f)
        ) {
            Surface(
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.TopCenter)
                    .zIndex(1f),
                color = Color.hsl(0f, 0f, .99f, 1f),
                shape = CircleShape
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(ranking[0].urlImage)
                        .crossfade(true)
                        .build(),
                    error = painterResource(R.drawable.nota_social_typho),
                    placeholder = painterResource(R.drawable.loading_img),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                    )
                    .fillMaxWidth()
                    .height(200.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = ranking[0].name,
                    fontFamily = ralewayFamily,
                    color = Color.Black
                )
                Row() {
                    Icon(
                        painter = painterResource(id = R.drawable.receipt_solid),
                        contentDescription = "",
                        modifier = Modifier.size(12.dp),
                        tint = Color.hsl(123f, .63f, .33f, 1f)
                    )
                    Text(
                        text = "${ranking[0].totalReceipts}",
                        modifier = Modifier.padding(start = 5.dp),
                        fontFamily = interFamily,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.product_solid),
                        contentDescription = "",
                        modifier = Modifier.size(12.dp),
                        tint = Color.hsl(123f, .63f, .33f, 1f)
                    )
                    Text(
                        text = "${ranking[0].totalProducts}",
                        modifier = Modifier.padding(start = 5.dp),
                        color = Color.Black,
                        fontFamily = interFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 10.sp
                    )
                }
                Column(
                    modifier = Modifier.padding(top = 10.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.gold_medal),
                        contentDescription = "",
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }


        Box(
            modifier = Modifier
                .weight(1f)
                .height(150.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Surface(
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.TopCenter)
                    .zIndex(1f),
                color = Color.hsl(0f, 0f, .99f, 1f),
                shape = CircleShape
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(ranking[2].urlImage)
                        .crossfade(true)
                        .build(),
                    error = painterResource(R.drawable.nota_social_typho),
                    placeholder = painterResource(R.drawable.loading_img),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp)
                    )
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (ranking.size > 2) {
                    Text(
                        text = "${ranking[2].name}",
                        fontFamily = ralewayFamily,
                        color = Color.Black
                    )
                    Row(
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.receipt_solid),
                            contentDescription = "",
                            modifier = Modifier.size(12.dp),
                            tint = Color.hsl(123f, .63f, .33f, 1f)
                        )
                        Text(
                            text = "${ranking[2].totalReceipts}",
                            modifier = Modifier.padding(start = 5.dp),
                            color = Color.Black,
                            fontFamily = interFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 10.sp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.product_solid),
                            contentDescription = "",
                            modifier = Modifier.size(12.dp),
                            tint = Color.hsl(123f, .63f, .33f, 1f)
                        )
                        Text(
                            text = "${ranking[2].totalProducts}",
                            modifier = Modifier.padding(start = 5.dp),
                            color = Color.Black,
                            fontFamily = interFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 10.sp
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.bronze_medal),
                        contentDescription = "",
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PageListPreview() {
    NotasocialTheme {
        PageList()
    }
}