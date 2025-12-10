package edu.ucne.morenofootball.ui.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import edu.ucne.morenofootball.ui.presentation.agradecimiento.AgradecimientoScreen
import edu.ucne.morenofootball.ui.presentation.carrito.CarritoScreen
import edu.ucne.morenofootball.ui.presentation.carrito.CarritoViewModel
import edu.ucne.morenofootball.ui.presentation.checkout.CheckoutScreen
import edu.ucne.morenofootball.ui.presentation.composables.BottomBar
import edu.ucne.morenofootball.ui.presentation.composables.TopBar
import edu.ucne.morenofootball.ui.presentation.home.HomeScreen
import edu.ucne.morenofootball.ui.presentation.login.LoginScreen
import edu.ucne.morenofootball.ui.presentation.miCuenta.MiCuentaScreen
import edu.ucne.morenofootball.ui.presentation.misPedidos.PedidoDetalleScreen
import edu.ucne.morenofootball.ui.presentation.misPedidos.PedidoScreen
import edu.ucne.morenofootball.ui.presentation.productoDetalles.ProductoDetalleScreen

@Composable
fun MorenoFootBallNavHost() {
    val nav = rememberNavController()
    val navBackStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showTopBar = remember(currentRoute) {
        currentRoute != null && !currentRoute.contains("Login", ignoreCase = true)
    }

    val showBottomBar = remember(currentRoute) {
        currentRoute != null && !currentRoute.contains("Login", ignoreCase = true)
    }

    val carritoViewModel: CarritoViewModel = hiltViewModel()
    val cartItemCount by carritoViewModel.itemCount.collectAsStateWithLifecycle(
        initialValue = 0,
    )

    AppScreen(
        nav = nav,
        topBar = {
            if (showTopBar) {
                TopBar(
                    navController = nav,
                    onCartClick = { },
                    cartItemCount = cartItemCount
                )
            }
        },
        bottomBar = {
            if (showBottomBar)
                BottomBar(nav)
        }
    )
}

@Composable
fun AppScreen(
    nav: NavHostController,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = topBar,
        bottomBar = bottomBar,
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = nav,
            startDestination = Screen.Login
        ) {
            composable<Screen.Login> {
                LoginScreen(
                    navigateToHome = {
                        nav.navigate(Screen.Home) {
                            popUpTo(Screen.Login) { inclusive = true }
                        }
                    }
                )
            }

            composable<Screen.Checkout> {
                val args = it.toRoute<Screen.Checkout>()
                CheckoutScreen(
                    navigateToAgradecimiento = {
                        nav.navigate(Screen.Agradecimiento) {
                            popUpTo(Screen.Checkout(args.productoId)) { inclusive = true }
                        }
                    },
                    productoId = args.productoId
                )
            }

            composable<Screen.Agradecimiento> {
                AgradecimientoScreen(
                    onGoToPedidos = { nav.navigate(Screen.Pedidos) },
                    onGoToHome = { nav.navigate(Screen.Home) }
                )
            }

            composable<Screen.Home> {
                HomeScreen(
                    navigateToLogin = {
                        nav.navigate(Screen.Home) {
                            popUpTo(Screen.Login) { inclusive = true }
                        }
                    },
                    onProductClick = { productoId ->
                        nav.navigate(Screen.ProductoDetalle(productoId))
                    }
                )
            }

            composable<Screen.Pedidos> {
                PedidoScreen(
                    navigateToDetalle = { detalleId ->
                        nav.navigate(Screen.PedidoDetalleScreen(detalleId))
                    }
                )
            }

            composable<Screen.PedidoDetalleScreen> { backStackEntry ->
                val pedidoId = backStackEntry.toRoute<Screen.PedidoDetalleScreen>().pedidoId
                PedidoDetalleScreen(pedidoId = pedidoId)
            }

            composable<Screen.ProductoDetalle> { backStackEntry ->
                val args = backStackEntry.toRoute<Screen.ProductoDetalle>()
                ProductoDetalleScreen(
                    productoId = args.productoId,
                    onProductClick = { productoId ->
                        nav.navigate(Screen.ProductoDetalle(productoId))
                    },
                    onCheckoutProductClick = {
                        nav.navigate(Screen.Checkout(args.productoId))
                    }
                )
            }

            composable<Screen.Carrito> {
                CarritoScreen(
                    navigateToHome = {
                        nav.navigate(Screen.Home)
                    },
                    navigateToCheckout = {
                        nav.navigate(Screen.Checkout(0))
                    }
                )
            }

            composable<Screen.MiCuenta> {
                MiCuentaScreen(
                    navigateToLogin = {
                        nav.navigate(Screen.Login) {
                            popUpTo(Screen.Login) { inclusive = true }
                        }
                    },
                    navigateToPedidos = {
                        nav.navigate(Screen.Pedidos)
                    }
                )
            }
        }
    }

}