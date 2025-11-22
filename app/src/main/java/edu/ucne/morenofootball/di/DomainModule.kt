package edu.ucne.morenofootball.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.morenofootball.domain.usuarios.useCases.GetUsuarioLoggeadoUseCase
import edu.ucne.morenofootball.domain.usuarios.useCases.LoginUseCase
import edu.ucne.morenofootball.domain.usuarios.useCases.ModificarCredencialesUseCase
import edu.ucne.morenofootball.domain.usuarios.useCases.RegisterUseCase
import edu.ucne.morenofootball.domain.usuarios.useCases.UsuarioUseCases
import edu.ucne.morenofootball.domain.usuarios.useCases.ValidarLoginRegisterUseCase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {
    @Provides
    @Singleton
    fun providesUsuarioUseCases(
        registerUseCase: RegisterUseCase,
        loginUseCase: LoginUseCase,
        modificarCredencialesUseCase: ModificarCredencialesUseCase,
        validarLoginRegisterUseCase: ValidarLoginRegisterUseCase,
        getUsuarioLoggeadoUseCase: GetUsuarioLoggeadoUseCase
    ) = UsuarioUseCases(
        registerUseCase = registerUseCase,
        loginUseCase = loginUseCase,
        modificarCredencialesUseCase = modificarCredencialesUseCase,
        validarLoginRegisterUseCase = validarLoginRegisterUseCase,
        getUsuarioLoggeadoUseCase = getUsuarioLoggeadoUseCase,
    )
}