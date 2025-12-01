package edu.ucne.morenofootball.ui.presentation.navigation

// Estos son los imports CORRECTOS:
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import edu.ucne.morenofootball.ui.presentation.composables.BottomBar
import edu.ucne.morenofootball.ui.presentation.composables.TopBar
import edu.ucne.morenofootball.ui.presentation.home.HomeScreen
import edu.ucne.morenofootball.ui.presentation.login.LoginScreen

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

    AppScreen(
        nav = nav,
        topBar = {
            if (showTopBar) {
                TopBar(
                    navController = nav,
                    onCartClick = { },
                    cartItemCount = 50
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
        }
    }

}