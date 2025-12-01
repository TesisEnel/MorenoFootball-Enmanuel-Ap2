package edu.ucne.morenofootball.ui.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Login : Screen()

    @Serializable
    data object Home : Screen()

    @Serializable
    data object Carrito : Screen()

    @Serializable
    data object ListaDeDeseos : Screen()

    @Serializable
    data object Pedidos : Screen()

    @Serializable
    data object MiCuenta : Screen()
}