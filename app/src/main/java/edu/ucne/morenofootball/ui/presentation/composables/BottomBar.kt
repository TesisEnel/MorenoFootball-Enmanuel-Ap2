package edu.ucne.morenofootball.ui.presentation.composables

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AccountCircle
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import edu.ucne.morenofootball.ui.presentation.navigation.Screen

@Composable
fun BottomBar(nav: NavHostController) {
    val currentDestination = nav.currentBackStackEntryAsState().value?.destination

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        tonalElevation = 8.dp
    ) {
        var icon: ImageVector = Icons.TwoTone.Home
        listOf(
            Screen.Home,
            Screen.Carrito,
            Screen.ListaDeDeseos,
            Screen.MiCuenta
        ).forEach { screen ->
            when (screen) {
                Screen.Home -> icon = Icons.TwoTone.Home
                Screen.Carrito -> icon = Icons.TwoTone.ShoppingCart
                Screen.ListaDeDeseos -> icon = Icons.TwoTone.Favorite
                Screen.MiCuenta -> icon = Icons.TwoTone.AccountCircle
                else -> {}
            }
            val selected = currentDestination?.route == screen::class.qualifiedName

            NavigationBarItem(
                selected = selected,
                onClick = {
                    nav.navigate(screen) {
                        // Configuración para evitar múltiples instancias
                        launchSingleTop = true
                        restoreState = true

                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(nav.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = "",
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(text = screen::class.simpleName ?: "") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}