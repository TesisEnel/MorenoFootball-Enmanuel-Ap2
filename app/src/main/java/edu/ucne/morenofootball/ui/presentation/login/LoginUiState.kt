package edu.ucne.morenofootball.ui.presentation.login

data class LoginUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorUsername: String? = null,
    val errorEmail: String? = null,
    val errorPass: String? = null,
    val errorConfirmPass: String? = null,
    val errorPassVisible: String? = null,
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isUserHaveAnAccount: Boolean = true,
    val rememberUser: Boolean = true,
    val message: String? = null,
)