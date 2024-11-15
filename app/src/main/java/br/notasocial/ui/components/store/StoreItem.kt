package br.notasocial.ui.components.store

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.data.model.Store
import br.notasocial.ui.theme.ralewayFamily

@Composable
fun StoreItem(
    store: Store,
    navigateToStore: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(color = Color.White, shape = RoundedCornerShape(15.dp))
            .padding(25.dp)
            .clickable { navigateToStore() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.carrefour_logo),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = store.name,
            fontFamily = ralewayFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun StoreItemPreview() {
    val mockStore = Store(1, "Carrefour", "Address 01", "(11) 1234-5678")
    StoreItem(
        store = mockStore,
        navigateToStore = {}
    )
}