package edu.ucne.morenofootball.ui.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Login : Screen()

    @Serializable
    data object Home : Screen()

    @Serializable
    data object Register : Screen()
}