package br.notasocial.ui.navigation

import QrCodeDestination
import QrCodeScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.notasocial.ui.view.CatalogDestination
import br.notasocial.ui.view.CatalogScreen
import br.notasocial.ui.view.QrCodeResultDestination
import br.notasocial.ui.view.QrCodeResultScreen

@Composable
fun NotaSocialNavHost(
    checkCameraPermission: () -> Unit,
    textResult: MutableState<String>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = QrCodeDestination.route,
        modifier = modifier
    ) {
        composable(route = QrCodeDestination.route) {
            QrCodeScreen(
                onRequestPermission = checkCameraPermission,
                textResult = textResult,
                navigateToQrCodeResult = { navController.navigate("${QrCodeResultDestination.route}/$it") }
            )
        }
        composable(route = CatalogDestination.route) {
            CatalogScreen()
        }
        composable(
            route = QrCodeResultDestination.routeWithArgs,
            arguments = listOf(navArgument(QrCodeResultDestination.receiptIdArg) {
                type = NavType.StringType
            })
        ) {
            textResult.value = ""
            QrCodeResultScreen(
                onRequestPermission = checkCameraPermission,
                textResult = textResult,
                navigateToCatalog = { navController.navigate(CatalogDestination.route) },
                navigateToQrCodeResult = { navController.navigate("${QrCodeResultDestination.route}/$it") }
            )
        }
    }
}