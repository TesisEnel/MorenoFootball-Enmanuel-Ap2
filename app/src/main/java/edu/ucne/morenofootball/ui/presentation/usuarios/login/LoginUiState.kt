package edu.ucne.morenofootball.ui.presentation.usuarios.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val errorEmail: String? = null,
    val errorPass: String? = null,
    val errorPassVisible: String? = null,
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val message: String? = null,
)