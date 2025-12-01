package edu.ucne.morenofootball.ui.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.ucne.morenofootball.ui.presentation.home.HomeScreen
import edu.ucne.morenofootball.ui.presentation.login.LoginScreen

@Composable
fun MorenoFootBallNavHost( nav: NavHostController = rememberNavController()) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = nav ,
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