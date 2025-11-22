package edu.ucne.morenofootball.domain.usuarios.useCases

data class UsuarioUseCases(
    val registerUseCase: RegisterUseCase,
    val loginUseCase: LoginUseCase,
    val modificarCredencialesUseCase: ModificarCredencialesUseCase,
    val validarLoginRegisterUseCase: ValidarLoginRegisterUseCase,
    val getUsuarioLoggeadoUseCase: GetUsuarioLoggeadoUseCase
)