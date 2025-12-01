package edu.ucne.morenofootball.ui.presentation.login

interface LoginUiEvent {
    data class Login(
        val email: String,
        val password: String,
        val rememberUser: Boolean
    ): LoginUiEvent

    data class Register(
        val usermame: String,
        val email: String,
        val password: String,
        val confirmPassword: String,
    ): LoginUiEvent

    data class OnUsernameChange(val username: String): LoginUiEvent
    data class OnEmailChange(val email: String): LoginUiEvent
    data class OnPasswordChange(val password: String): LoginUiEvent
    data class OnConfirmPasswordChange(val confirmPassword: String): LoginUiEvent
    data object OnTogglePasswordVisibility: LoginUiEvent
    data object OnChangeIsUserHaveAnAccount: LoginUiEvent
    data object OnToggleRememberUser: LoginUiEvent
}