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
import edu.ucne.morenofootball.ui.presentation.carrito.CarritoScreen
import edu.ucne.morenofootball.ui.presentation.carrito.CarritoViewModel
import edu.ucne.morenofootball.ui.presentation.composables.BottomBar
import edu.ucne.morenofootball.ui.presentation.composables.TopBar
import edu.ucne.morenofootball.ui.presentation.home.HomeScreen
import edu.ucne.morenofootball.ui.presentation.login.LoginScreen
import edu.ucne.morenofootball.ui.presentation.miCuenta.MiCuentaScreen

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
    val cartItemCount by carritoViewModel.itemCount.collectAsStateWithLifecycle()

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
    floatingActionButton: @Composable () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = topBar,
        bottomBar = bottomBar
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

            composable<Screen.Home> {
                HomeScreen(
                    navigateToLogin = {
                        nav.navigate(Screen.Home) {
                            popUpTo(Screen.Login) { inclusive = true }
                        }
                    }
                )
            }

            composable<Screen.Carrito> {
                CarritoScreen()
            }

            composable<Screen.MiCuenta> {
                MiCuentaScreen(
                    navigateToLogin = {
                        nav.navigate(Screen.Login) {
                            popUpTo(Screen.Login) { inclusive = true }
                        }
                    }
                )
            }
        }
    }

}