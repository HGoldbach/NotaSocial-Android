package br.notasocial.ui.components.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily

@Composable
fun FollowUserItem(
    navigateToProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.size(100.dp).clickable { navigateToProfile() },
            color = Color.White,
            shape = CircleShape
        ) {
            Icon(
                painter = painterResource(id = R.drawable.user_solid),
                contentDescription = "",
                tint = Color.hsl(123f, .63f, .33f, 1f),
                modifier = Modifier.size(40.dp).padding(30.dp)
            )
        }
        Text(
            text = "Maria Jose",
            fontSize = 14.sp,
            color = Color.Black,
            fontFamily = ralewayFamily,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 10.dp).fillMaxWidth()
        )
    }
}

@Composable
@Preview(showBackground = true)
fun FollowUserItemPreview() {
    NotasocialTheme {
        FollowUserItem(
            navigateToProfile = {}
        )
    }
}