package br.notasocial.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.notasocial.R
import br.notasocial.ui.navigation.NotaSocialNavHost
import br.notasocial.ui.theme.NotasocialTheme

@Composable
fun NotaSocialApp(
    checkCameraPermission: () -> Unit,
    textResult: MutableState<String>,
    navController: NavHostController = rememberNavController(),
) {
    NotaSocialNavHost(checkCameraPermission = checkCameraPermission, textResult = textResult, navController = navController)
}


@Composable
fun NotaSocialBottomAppBar(

) {
    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.height(60.dp)
    ) {
        NavigationBarItem(
            selected = true,
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.home_solid),
                    contentDescription = "",
                    modifier = Modifier.size(15.dp)
                )
            },
        )
        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.search_regular),
                    contentDescription = "",
                    modifier = Modifier.size(15.dp)
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.user_regular),
                    contentDescription = "",
                    modifier = Modifier.size(15.dp)
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NotaSocialBottomAppBarPreview() {
    NotasocialTheme {
        NotaSocialBottomAppBar()
    }
}
