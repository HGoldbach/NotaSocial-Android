package br.notasocial.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.notasocial.ui.navigation.NotaSocialNavHost

@Composable
fun NotaSocialApp(
    checkCameraPermission: () -> Unit,
    textResult: MutableState<String>,
    navController: NavHostController = rememberNavController(),
) {
    NotaSocialNavHost(checkCameraPermission = checkCameraPermission, textResult = textResult, navController = navController)
}

