package br.notasocial.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.notasocial.R
import br.notasocial.ui.navigation.NotaSocialNavHost
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.view.customer.contact.ContactDestination
import br.notasocial.ui.view.customer.home.HomeDestination
import br.notasocial.ui.view.customer.qrcode.QrCodeDestination
import br.notasocial.ui.view.customer.ranking.RankingDestination
import br.notasocial.ui.view.customer.searchproduct.SearchProductDestination
import br.notasocial.ui.view.customer.searchstore.SearchStoreDestination
import br.notasocial.ui.view.customer.shoplist.ShopListDestination
import br.notasocial.ui.view.customer.signin.SignInDestination
import br.notasocial.ui.view.customer.signup.SignUpScreenDestination
import br.notasocial.ui.view.customer.userprofile.FavoritesDestination
import br.notasocial.ui.view.customer.userprofile.UserProfileHomeDestination
import br.notasocial.ui.view.store.StoreAddressesDestination
import br.notasocial.ui.view.store.StoreHomeDestination
import br.notasocial.ui.view.store.StorePromotionsDestination
import br.notasocial.ui.viewmodel.MainUiState
import br.notasocial.ui.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NotaSocialApp(
    checkCameraPermission: () -> Unit,
    textResult: MutableState<String>,
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    var showNavHost by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1000)
        showNavHost = true
    }

    LaunchedEffect(currentDestination) {
        if (drawerState.isOpen) {
            scope.launch {
                drawerState.close()
            }
        }
    }

    val showDrawer = currentDestination !in listOf(
        SignInDestination.route,
        SignUpScreenDestination.route
    )

    if (showDrawer) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    MenuSection(
                        role = uiState.role,
                        navigateToBuscarProduto = { navController.navigate(SearchProductDestination.route) },
                        navigateToEstabelecimentos = { navController.navigate(SearchStoreDestination.route) },
                        navigateToRanking = { navController.navigate(RankingDestination.route) },
                        navigateToFavoritos = { navController.navigate(FavoritesDestination.route) },
                        navigateToShoplist = { navController.navigate(ShopListDestination.route) },
                        navigateToCadastrarNota = { navController.navigate(QrCodeDestination.route) },
                        navigateToLogin = { navController.navigate(SignInDestination.route) },
                        navigateToRegistrar = { navController.navigate(SignUpScreenDestination.route) },
                        navigateToPromotions = { navController.navigate(StorePromotionsDestination.route) },
                        navigateToAddresses = { navController.navigate(StoreAddressesDestination.route) },
                        navigateToContact = { navController.navigate(ContactDestination.route) },
                        logout = { viewModel.logout() }
                    )
                }
            },
            content = {
                if (showNavHost) {
                    Scaffold(
                        topBar = {
                            NotaSocialTopAppBar(
                                isMenuActive = drawerState.isOpen,
                                onMenuToggle = {
                                    scope.launch {
                                        if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                    }
                                },
                                navigateToHome = {
                                    if (uiState.role == "STORE") {
                                        navController.navigate(StoreHomeDestination.route)
                                    } else {
                                        navController.navigate(HomeDestination.route)
                                    }
                                }
                            )
                        },
                        bottomBar = {
                            NotaSocialBottomAppBar(
                                navigateToBuscarProduto = {
                                    navController.navigate(
                                        SearchProductDestination.route
                                    )
                                },
                                navigateToPerfilProprio = {
                                    navController.navigate(
                                        UserProfileHomeDestination.route
                                    )
                                },
                                navigateToRanking = { navController.navigate(RankingDestination.route) },
                                navigateToHome = { navController.navigate(HomeDestination.route) },
                                navigateToAddresses = { navController.navigate(StoreAddressesDestination.route) },
                                navigateToPromotions = {
                                    navController.navigate(
                                        StorePromotionsDestination.route
                                    )
                                },
                            )
                        }
                    ) { innerPadding ->
                        NotaSocialNavHost(
                            checkCameraPermission = checkCameraPermission,
                            textResult = textResult,
                            navController = navController,
                            role = uiState.role,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.nota_social_rounded),
                            contentDescription = ""
                        )
                    }
                }
            }
        )
    } else {
        MainContent(
            checkCameraPermission = checkCameraPermission,
            textResult = textResult,
            navController = navController,
            uiState = uiState,
        )
    }
}

@Composable
fun MainContent(
    checkCameraPermission: () -> Unit,
    textResult: MutableState<String>,
    navController: NavHostController,
    uiState: MainUiState,
) {
    Scaffold { innerPadding ->
        NotaSocialNavHost(
            checkCameraPermission = checkCameraPermission,
            textResult = textResult,
            navController = navController,
            role = uiState.role,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun NotaSocialTopAppBar(
    isMenuActive: Boolean = false,
    onMenuToggle: () -> Unit = {},
    navigateToHome: () -> Unit = {},
) {
    Navbar(
        isMenuActive = isMenuActive,
        onMenuToggle = onMenuToggle,
        navigateToHome = navigateToHome
    )
}

@Composable
fun MenuSection(
    role: String,
    navigateToBuscarProduto: () -> Unit,
    navigateToEstabelecimentos: () -> Unit,
    navigateToRanking: () -> Unit,
    navigateToFavoritos: () -> Unit,
    navigateToShoplist: () -> Unit,
    navigateToCadastrarNota: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToRegistrar: () -> Unit,
    navigateToPromotions: () -> Unit,
    navigateToAddresses: () -> Unit,
    navigateToContact: () -> Unit,
    logout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(250.dp)
            .background(Color.White)
    ) {
        if (role == "CUSTOMER") {
            MenuItem(
                text = "Buscar Produto",
                icon = R.drawable.search_regular,
                modifier = Modifier
                    .padding(top = 65.dp)
                    .clickable { navigateToBuscarProduto() }
            )
            MenuItem(
                text = "Estabelecimentos",
                icon = R.drawable.building_solid,
                modifier = Modifier.clickable { navigateToEstabelecimentos() }
            )
            MenuItem(
                text = "Ranking Usuários",
                icon = R.drawable.ranking_solid,
                modifier = Modifier.clickable { navigateToRanking() }
            )
            MenuItem(
                text = "Favoritos",
                icon = R.drawable.heart_solid,
                modifier = Modifier.clickable { navigateToFavoritos() }
            )
            MenuItem(
                text = "Carrinho",
                icon = R.drawable.basket_solid,
                modifier = Modifier.clickable { navigateToShoplist() }
            )
            MenuItem(
                text = "Cadastrar Nota",
                icon = R.drawable.receipt,
                modifier = Modifier.clickable { navigateToCadastrarNota() }
            )
            MenuItem(
                text = "Sair",
                icon = R.drawable.signout_solid,
                modifier = Modifier.clickable { logout() }
            )
            Spacer(modifier = Modifier.weight(1f))
            MenuItem(
                text = "Contato",
                icon = R.drawable.circle_solid,
                modifier = Modifier.clickable { navigateToContact() }.padding(bottom = 20.dp)
            )
        }

        if (role == "") {
            MenuItem(
                text = "Buscar Produto",
                icon = R.drawable.search_regular,
                modifier = Modifier
                    .padding(top = 65.dp)
                    .clickable { navigateToBuscarProduto() }
            )
            MenuItem(
                text = "Ranking Usuários",
                icon = R.drawable.ranking_solid,
                modifier = Modifier.clickable { navigateToRanking() }
            )
            MenuItem(
                text = "Entrar",
                icon = R.drawable.signin_solid,
                modifier = Modifier.clickable { navigateToLogin() }
            )
            MenuItem(
                text = "Registrar",
                icon = R.drawable.signup_solid,
                modifier = Modifier.clickable { navigateToRegistrar() }
            )
            Spacer(modifier = Modifier.weight(1f))
            MenuItem(
                text = "Contato",
                icon = R.drawable.circle_solid,
                modifier = Modifier.clickable { navigateToContact() }.padding(bottom = 20.dp)
            )
        }

        if (role == "STORE") {
            MenuItem(
                text = "Promoções",
                icon = R.drawable.promotion_solid,
                modifier = Modifier
                    .padding(top = 65.dp)
                    .clickable { navigateToPromotions() }
            )
            MenuItem(
                text = "Endereços",
                icon = R.drawable.location_solid,
                modifier = Modifier.clickable { navigateToAddresses() }
            )
            MenuItem(
                text = "Sair",
                icon = R.drawable.signout_solid,
                modifier = Modifier.clickable {
                    logout()
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            MenuItem(
                text = "Contato",
                icon = R.drawable.circle_solid,
                modifier = Modifier.clickable { navigateToContact() }.padding(bottom = 20.dp)
            )
        }

    }
}

@Composable
fun MenuItem(
    text: String,
    icon: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .width(180.dp)
            .height(50.dp)
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Surface(
            modifier = Modifier.padding(start = 20.dp),
            color = Color.Transparent
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = "",
                tint = Color.Black,
                modifier = Modifier.size(16.dp),
            )
        }
        Text(
            text = text,
            color = Color.Black,
            fontFamily = ralewayFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}

@Composable
fun Navbar(
    modifier: Modifier = Modifier,
    isMenuActive: Boolean,
    navigateToHome: () -> Unit,
    onMenuToggle: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color = Color.White),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.nota_social_typho),
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .clickable { navigateToHome() }
            )
            Surface(
                modifier = Modifier
                    .padding(5.dp)
                    .height(35.dp)
                    .width(45.dp),
                shape = RoundedCornerShape(5.dp),
                color = Color.hsl(123f, .63f, .33f, 1f)

            ) {
                IconButton(
                    onClick = { onMenuToggle() },
                    modifier = Modifier
                        .padding(5.dp)
                        .background(color = Color.Transparent)
                        .size(40.dp)
                ) {
                    Icon(
                        painter = if (isMenuActive) {
                            painterResource(id = R.drawable.close_xmark)
                        } else {
                            painterResource(id = R.drawable.menu_bars)
                        },
                        contentDescription = "",
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun NavbarPreview() {
    NotasocialTheme {
        Navbar(
            isMenuActive = false,
            onMenuToggle = {},
            navigateToHome = {}
        )
    }
}


@Composable
fun NotaSocialBottomAppBar(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit = {},
    navigateToBuscarProduto: () -> Unit = {},
    navigateToPerfilProprio: () -> Unit = {},
    navigateToRanking: () -> Unit = {},
    navigateToPromotions: () -> Unit = {},
    navigateToAddresses: () -> Unit = {},
    viewModel: MainViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val role = uiState.role

    NavigationBar(
        containerColor = Color.White,
        modifier = modifier.height(60.dp)
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navigateToHome() },
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
            onClick = {
                if (role == "STORE") {
                    navigateToPromotions()
                } else {
                    navigateToBuscarProduto()
                }
            },
            icon = {
                Icon(
                    painter = if (role == "STORE") {
                        painterResource(id = R.drawable.promotion_solid)
                    } else {
                        painterResource(id = R.drawable.search_regular)
                    },
                    contentDescription = "",
                    modifier = Modifier.size(15.dp)
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = {
                if (role == "CUSTOMER") {
                    navigateToPerfilProprio()
                } else if (role == "STORE") {
                    navigateToAddresses()
                } else {
                    navigateToRanking()
                }
            },
            icon = {
                Icon(
                    painter = if (role == "CUSTOMER") {
                        painterResource(id = R.drawable.user_regular)
                    } else if (role == "STORE") {
                        painterResource(id = R.drawable.location_solid)
                    } else {
                        painterResource(id = R.drawable.ranking_solid)
                    },
                    contentDescription = "",
                    modifier = Modifier.size(15.dp)
                )
            }
        )
    }
}

@Composable
fun ModalNavigationDrawerSample() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val items =
        listOf(
            Icons.Default.AccountCircle,
            Icons.Default.Email,
            Icons.Default.Favorite,
        )
    val selectedItem = remember { mutableStateOf(items[0]) }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        Spacer(Modifier.height(12.dp))
                        items.forEach { item ->
                            NavigationDrawerItem(
                                icon = { Icon(item, contentDescription = null) },
                                label = { Text(item.name.substringAfterLast(".")) },
                                selected = item == selectedItem.value,
                                onClick = {
                                    scope.launch { drawerState.close() }
                                    selectedItem.value = item
                                },
                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                            )
                        }
                    }
                }
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = if (drawerState.isClosed) ">>> Swipe >>>" else "<<< Swipe <<<")
                    Spacer(Modifier.height(20.dp))
                    Button(onClick = { scope.launch { drawerState.open() } }) { Text("Click to open") }
                }
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ModalNavigationDrawerSamplePreview() {
    NotasocialTheme {
        ModalNavigationDrawerSample()
    }
}

