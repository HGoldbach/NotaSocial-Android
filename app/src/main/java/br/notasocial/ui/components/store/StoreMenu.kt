package br.notasocial.ui.components.store

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.view.customer.storeprofile.StoreMenuItem

@Composable
fun StoreMenu(
    onMenuItemClick: (StoreMenuItem) -> Unit,
    selectedMenuItem: StoreMenuItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        StoreMenuItem(
            text = stringResource(id = R.string.promotion_menu_item),
            modifier = Modifier
                .weight(1f)
                .clickable { onMenuItemClick(StoreMenuItem.PROMOCOES) },
            isActive = selectedMenuItem == StoreMenuItem.PROMOCOES,
        )
        Spacer(modifier = Modifier.size(15.dp))
        StoreMenuItem(
            text = stringResource(id = R.string.address_menu_item),
            modifier = Modifier
                .weight(1f)
                .clickable { onMenuItemClick(StoreMenuItem.ENDERECOS) },
            isActive = selectedMenuItem == StoreMenuItem.ENDERECOS
        )
    }
}

@Composable
fun StoreMenuItem(
    text: String,
    modifier: Modifier = Modifier,
    isActive: Boolean = false,
) {
    Column(
        modifier = modifier
            .height(50.dp)
            .background(
                color = if (isActive) {
                    Color.hsl(123f, .63f, .33f, 1f)
                } else {
                    Color.White
                },
                shape = RoundedCornerShape(10.dp)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            fontFamily = ralewayFamily,
            fontSize = 15.sp,
            fontWeight = if (isActive) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            },
            color = if (isActive) {
                Color.White
            } else {
                Color.Black
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun StoreMenuPreview() {
    NotasocialTheme {
        StoreMenu(
            onMenuItemClick = {},
            selectedMenuItem = StoreMenuItem.PROMOCOES
        )
    }
}

@Composable
@Preview(showBackground = true)
fun StoreMenuItemPreview() {
    NotasocialTheme {
        StoreMenuItem(
            text = stringResource(id = R.string.promotion_menu_item),
        )
    }
}

@Composable
@Preview(name = "active", showBackground = true)
fun StoreMenuItemActivePreview() {
    NotasocialTheme {
        StoreMenuItem(
            text = stringResource(id = R.string.promotion_menu_item),
            isActive = true
        )
    }
}