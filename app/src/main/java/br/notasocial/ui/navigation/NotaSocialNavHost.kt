package br.notasocial.ui.navigation

import br.notasocial.ui.view.qrcode.QrCodeDestination
import br.notasocial.ui.view.qrcode.QrCodeScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.notasocial.ui.view.CatalogDestination
import br.notasocial.ui.view.CatalogScreen
import br.notasocial.ui.view.home.HomeScreen
import br.notasocial.ui.view.home.HomeScreenDestination
import br.notasocial.ui.view.login.LoginScreen
import br.notasocial.ui.view.login.LoginScreenDestination
import br.notasocial.ui.view.qrcode.QrCodeResultDestination
import br.notasocial.ui.view.qrcode.QrCodeResultScreen
import br.notasocial.ui.view.registar.RegistrarScreen
import br.notasocial.ui.view.registar.RegistrarScreenDestination

@Composable
fun NotaSocialNavHost(
    checkCameraPermission: () -> Unit,
    textResult: MutableState<String>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeScreenDestination.route,
        modifier = modifier
    ) {

        composable(route = HomeScreenDestination.route) {
            HomeScreen(
                navigateToLogin = { navController.navigate(LoginScreenDestination.route) },
                navigateToRegistrar = { navController.navigate(RegistrarScreenDestination.route) }
            )
        }

        composable(route = LoginScreenDestination.route) {
            LoginScreen(
                navigateToRegistrar = { navController.navigate(RegistrarScreenDestination.route) },
                navigateUp = { navController.navigateUp() }
            )
        }

        composable(route = RegistrarScreenDestination.route) {
            RegistrarScreen(
                navigateToLogin = { navController.navigate(LoginScreenDestination.route) },
                navigateUp = { navController.navigateUp() }
            )
        }

        composable(route = QrCodeDestination.route) {
            QrCodeScreen(
                onRequestPermission = checkCameraPermission,
                textResult = textResult,
                navigateToQrCodeResult = { navController.navigate("${QrCodeResultDestination.route}/$it") }
            )
        }
        composable(route = CatalogDestination.route) {
            CatalogScreen(
                navigateToQrCode = { navController.navigate(QrCodeDestination.route) }
            )
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