package edu.ucne.morenofootball.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.morenofootball.domain.usuarios.useCases.*
import edu.ucne.morenofootball.domain.productos.useCases.*
import edu.ucne.morenofootball.domain.tarjetas.useCases.*
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
        getUsuarioLoggeadoUseCase: GetUsuarioLoggeadoUseCase,
        logoutUseCase: LogoutUseCase
    ) = UsuarioUseCases(
        registerUseCase = registerUseCase,
        loginUseCase = loginUseCase,
        modificarCredencialesUseCase = modificarCredencialesUseCase,
        validarLoginRegisterUseCase = validarLoginRegisterUseCase,
        getUsuarioLoggeadoUseCase = getUsuarioLoggeadoUseCase,
        logoutUseCase = logoutUseCase
    )

    @Provides
    @Singleton
    fun providesProductoUseCases(
        listByAvailabilityUseCase: ListByAvailabilityUseCase,
        listByTipoUseCase: ListByTipoUseCase,
        saveUseCase: SaveUseCase,
        editUseCase: EditUseCase,
        deleteUseCase: DeleteUseCase
    ): ProductoUseCases = ProductoUseCases(
        listByAvailability = listByAvailabilityUseCase,
        listByTipo = listByTipoUseCase,
        save = saveUseCase,
        edit = editUseCase,
        delete = deleteUseCase
    )

    @Provides
    @Singleton
    fun providesTarjetaUseCases(
        listTarjetasByUsuarioIdUsecase: ListTarjetasByUsuarioIdUsecase,
        saveTarjetaUseCase: SaveTarjetaUseCase,
        editTarjetaUseCase: EditTarjetaUseCase,
        deleteTarjetaUseCase: DeleteTarjetaUseCase
    ): TarjetaUseCases = TarjetaUseCases(
        listTarjetasByUsuarioIdUsecase = listTarjetasByUsuarioIdUsecase,
        saveTarjetaUseCase = saveTarjetaUseCase,
        editTarjetaUseCase = editTarjetaUseCase,
        deleteTarjetaUseCase = deleteTarjetaUseCase
    )

}