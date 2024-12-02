package br.notasocial.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.notasocial.ui.view.customer.searchproduct.SearchProductDestination
import br.notasocial.ui.view.customer.searchproduct.SearchProductScreen
import br.notasocial.ui.view.customer.storeprofile.StoreProfileDestination
import br.notasocial.ui.view.customer.storeprofile.StoreProfileScreen
import br.notasocial.ui.view.customer.storepromotion.StorePromotionDestination
import br.notasocial.ui.view.customer.storepromotion.StorePromotionScreen
import br.notasocial.ui.view.customer.home.HomeDestination
import br.notasocial.ui.view.customer.home.HomeScreen
import br.notasocial.ui.view.customer.shoplist.ShopListDestination
import br.notasocial.ui.view.customer.shoplist.ShopListScreen
import br.notasocial.ui.view.customer.signin.SignInScreen
import br.notasocial.ui.view.customer.signin.SignInDestination
import br.notasocial.ui.view.customer.profile.ProfileDestination
import br.notasocial.ui.view.customer.profile.ProfileScreen
import br.notasocial.ui.view.customer.userprofile.AccountDestination
import br.notasocial.ui.view.customer.userprofile.AccountScreen
import br.notasocial.ui.view.customer.userprofile.FavoritesDestination
import br.notasocial.ui.view.customer.userprofile.FavoritesScreen
import br.notasocial.ui.view.customer.userprofile.UserProfileHomeDestination
import br.notasocial.ui.view.customer.userprofile.UserHomeScreen
import br.notasocial.ui.view.customer.userprofile.PerfilNotasDestination
import br.notasocial.ui.view.customer.userprofile.PerfilNotasScreen
import br.notasocial.ui.view.customer.userprofile.NotificationsDestination
import br.notasocial.ui.view.customer.userprofile.NotificationsScreen
import br.notasocial.ui.view.customer.userprofile.FollowersDestination
import br.notasocial.ui.view.customer.userprofile.FollowersScreen
import br.notasocial.ui.view.customer.userprofile.FollowingDestination
import br.notasocial.ui.view.customer.userprofile.FollowingScreen
import br.notasocial.ui.view.customer.product.ProductDestination
import br.notasocial.ui.view.customer.product.ProductScreen
import br.notasocial.ui.view.customer.qrcode.QrCodeDestination
import br.notasocial.ui.view.customer.qrcode.QrCodeResultDestination
import br.notasocial.ui.view.customer.qrcode.QrCodeResultScreen
import br.notasocial.ui.view.customer.qrcode.QrCodeScreen
import br.notasocial.ui.view.customer.ranking.RankingDestination
import br.notasocial.ui.view.customer.ranking.RankingScreen
import br.notasocial.ui.view.customer.signup.SignUpScreen
import br.notasocial.ui.view.customer.signup.SignUpScreenDestination
import br.notasocial.ui.view.customer.searchstore.SearchStoreDestination
import br.notasocial.ui.view.customer.searchstore.SearchStoreScreen
import br.notasocial.ui.view.store.PromotionDetailsScreen
import br.notasocial.ui.view.store.StoreAddressEditDestination
import br.notasocial.ui.view.store.StoreAddressEditScreen
import br.notasocial.ui.view.store.StoreAddressesDestination
import br.notasocial.ui.view.store.StoreAddressesScreen
import br.notasocial.ui.view.store.StoreHomeDestination
import br.notasocial.ui.view.store.StoreHomeScreen
import br.notasocial.ui.view.store.StorePromotionDetailsDestination
import br.notasocial.ui.view.store.StorePromotionEditDestination
import br.notasocial.ui.view.store.StorePromotionEditScreen
import br.notasocial.ui.view.store.StorePromotionsDestination
import br.notasocial.ui.view.store.StorePromotionsScreen

@Composable
fun NotaSocialNavHost(
    checkCameraPermission: () -> Unit,
    textResult: MutableState<String>,
    navController: NavHostController,
    role: String,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = if (role == "STORE") {
            StoreHomeDestination.route
        } else {
            HomeDestination.route
        },
        modifier = modifier
    ) {

        /*
           ****************************************************************************************************************
           ********************                        Perfil Consumidor                               ********************
           ****************************************************************************************************************
        */
        // Home
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToProduct = { navController.navigate("${ProductDestination.route}/$it") },
            )
        }

        // Buscar Produto
        composable(route = SearchProductDestination.route) {
            SearchProductScreen(
                navigateToProduct = { navController.navigate("${ProductDestination.route}/$it") }
            )
        }

        // Buscar Estabelecimento
        composable(route = SearchStoreDestination.route) {
            SearchStoreScreen(
                navigateToStore = { navController.navigate("${StoreProfileDestination.route}/$it") }
            )
        }

        // Estabelecimento Perfil
        composable(
            route = StoreProfileDestination.routeWithArgs,
            arguments = listOf(navArgument(StoreProfileDestination.storeIdArg) {
                type = NavType.StringType
            })
        ) {
            StoreProfileScreen(
                navigateToPromotion = { promotionId, storeName ->
                    navController.navigate("${StorePromotionDestination.route}/$promotionId/$storeName")
                }
            )
        }

        // Estabelecimento Promocao
        composable(
            route = StorePromotionDestination.routeWithArgs,
            arguments = listOf(
                navArgument(StorePromotionDestination.promotionIdArg) {
                    type = NavType.IntType
                },
                navArgument(StorePromotionDestination.storeNameArg) {
                    type = NavType.StringType
                }
            )) {
            StorePromotionScreen()
        }

        // Lista Produtos
        composable(route = ShopListDestination.route) {
            ShopListScreen(
                navigateToHome = { navController.navigate(HomeDestination.route) }
            )
        }

        // Perfil
        composable(
            route = ProfileDestination.routeWithArgs,
            arguments = listOf(navArgument(ProfileDestination.profileIdArg) {
                type = NavType.StringType
            })
        ) {
            ProfileScreen(
                navigateToProfile = { navController.navigate("${ProfileDestination.route}/$it") },
                navigateToProduct = { navController.navigate("${ProductDestination.route}/$it") }
            )
        }

        // Perfil Proprio
        composable(route = AccountDestination.route) {
            AccountScreen()
        }

        composable(route = FavoritesDestination.route) {
            FavoritesScreen(
                navigateToProduct = { navController.navigate("${ProductDestination.route}/$it") }
            )
        }

        composable(route = UserProfileHomeDestination.route) {
            UserHomeScreen(
                navigateToFavoritos = { navController.navigate(FavoritesDestination.route) },
                navigateToNotas = { navController.navigate(PerfilNotasDestination.route) },
                navigateToSeguindo = { navController.navigate(FollowingDestination.route) },
                navigateToSeguidores = { navController.navigate(FollowersDestination.route) },
                navigateToNotificacoes = { navController.navigate(NotificationsDestination.route) },
                navigateToConta = { navController.navigate(AccountDestination.route) }
            )
        }

        composable(route = PerfilNotasDestination.route) {
            PerfilNotasScreen()
        }

        composable(route = NotificationsDestination.route) {
            NotificationsScreen()
        }

        composable(route = FollowersDestination.route) {
            FollowersScreen(
                navigateToProfile = { navController.navigate("${ProfileDestination.route}/$it") },
            )
        }

        composable(route = FollowingDestination.route) {
            FollowingScreen(
                navigateToProfile = { navController.navigate("${ProfileDestination.route}/$it") },
            )
        }

        // Produto
        composable(
            route = ProductDestination.routeWithArgs,
            arguments = listOf(navArgument(ProductDestination.produtoIdArg) {
                type = NavType.StringType
            })
        ) {
            ProductScreen(
                navigateToProfile = { navController.navigate("${ProfileDestination.route}/$it") },
                navigateToProduct = { navController.navigate("${ProductDestination.route}/$it") }
            )
        }

        // QRCODE
        composable(route = QrCodeDestination.route) {
            QrCodeScreen(
                onRequestPermission = checkCameraPermission,
                textResult = textResult,
                navigateToQrCodeResult = { navController.navigate("${QrCodeResultDestination.route}/$it") }
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
                navigateToQrCode = { navController.navigate(QrCodeDestination.route) },
                navigateToQrCodeResult = { navController.navigate("${QrCodeResultDestination.route}/$it") }
            )
        }

        // Ranking
        composable(route = RankingDestination.route) {
            RankingScreen()
        }

        // Registrar
        composable(route = SignUpScreenDestination.route) {
            SignUpScreen(
                navigateToLogin = { navController.navigate(SignInDestination.route) },
                navigateUp = { navController.navigateUp() }
            )
        }

        composable(route = SignInDestination.route) {
            SignInScreen(
                navigateToHome = { navController.navigate(HomeDestination.route) },
                navigateToRegistrar = { navController.navigate(SignUpScreenDestination.route) },
                navigateUp = { navController.navigateUp() }
            )
        }

        /*
             ****************************************************************************************************************
             ********************                        Perfil Estabelecimento                          ********************
             ****************************************************************************************************************
        */

        composable(route = StoreHomeDestination.route) {
            StoreHomeScreen(
                navigateToAddresses = { navController.navigate(StoreAddressesDestination.route) },
                navigateToPromotions = { navController.navigate(StorePromotionsDestination.route) },
            )
        }

        composable(route = StoreAddressesDestination.route) {
            StoreAddressesScreen(
                navigateToAddressEdit = { navController.navigate(StoreAddressEditDestination.route) }
            )
        }

        composable(route = StorePromotionsDestination.route) {
            StorePromotionsScreen(
                navigateToPromotionDetails = { navController.navigate("${StorePromotionDetailsDestination.route}/$it") },
                navigateToPromotionEdit = { navController.navigate(StorePromotionEditDestination.route) }
            )
        }

        composable(
            route = StorePromotionDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(StorePromotionDetailsDestination.promotionIdArg) {
                type = NavType.IntType
            })
        ) {
            PromotionDetailsScreen()
        }

        composable(route = StorePromotionEditDestination.route) {
            StorePromotionEditScreen()
        }

        composable(route = StoreAddressEditDestination.route) {
            StoreAddressEditScreen()
        }
    }
}