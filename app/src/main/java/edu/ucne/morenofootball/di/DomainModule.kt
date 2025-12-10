package edu.ucne.morenofootball.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.morenofootball.domain.carritos.useCases.*
import edu.ucne.morenofootball.domain.pedidos.useCases.*
import edu.ucne.morenofootball.domain.productos.useCases.*
import edu.ucne.morenofootball.domain.tarjetas.useCases.*
import edu.ucne.morenofootball.domain.usuarios.useCases.*
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
        getByIdUseCase: GetByIdUseCase,
        listByTipoUseCase: ListByTipoUseCase,
        saveUseCase: SaveUseCase,
        editUseCase: EditUseCase,
        deleteUseCase: DeleteUseCase,
        listByIdsUseCase: ListByIdsUseCase
    ): ProductoUseCases = ProductoUseCases(
        listByAvailability = listByAvailabilityUseCase,
        getById = getByIdUseCase,
        listByTipo = listByTipoUseCase,
        save = saveUseCase,
        edit = editUseCase,
        delete = deleteUseCase,
        listByIds = listByIdsUseCase
    )

    @Provides
    @Singleton
    fun providesTarjetaUseCases(
        listTarjetasByUsuarioIdUsecase: ListTarjetasByUsuarioIdUsecase,
        saveTarjetaUseCase: SaveTarjetaUseCase,
        editTarjetaUseCase: EditTarjetaUseCase,
        deleteTarjetaUseCase: DeleteTarjetaUseCase,
        getByTarjetaIdUseCase: GetByTarjetaIdUseCase,
        tarjetaValidations: TarjetaValidations
    ): TarjetaUseCases = TarjetaUseCases(
        listTarjetasByUsuarioIdUsecase = listTarjetasByUsuarioIdUsecase,
        getByTarjetaIdUseCase = getByTarjetaIdUseCase,
        saveTarjetaUseCase = saveTarjetaUseCase,
        editTarjetaUseCase = editTarjetaUseCase,
        deleteTarjetaUseCase = deleteTarjetaUseCase,
        tarjetaValidations = tarjetaValidations
    )

    @Provides
    @Singleton
    fun providesCarritoUseCases(
        getByUsuarioIdUseCase: GetByUsuarioIdUseCase,
        getTotalCarritoUseCase: GetTotalCarritoUseCase,
        agregarProductoUseCase: AgregarProductoUseCase,
        aumentarCantidadUseCase: AumentarCantidadUseCase,
        disminuirCantidadUseCase: DisminuirCantidadUseCase,
        vaciarCarritoUseCase: VaciarCarritoUseCase,
        deleteProductUseCase: DeleteProductUseCase
    ): CarritoUseCases = CarritoUseCases(
        getByUsuarioIdUseCase = getByUsuarioIdUseCase,
        getTotalCarritoUseCase = getTotalCarritoUseCase,
        agregarProductoUseCase = agregarProductoUseCase,
        aumentarCantidadUseCase = aumentarCantidadUseCase,
        disminuirCantidadUseCase = disminuirCantidadUseCase,
        vaciarCarritoUseCase = vaciarCarritoUseCase,
        deleteProductUseCase = deleteProductUseCase
    )

    @Provides
    @Singleton
    fun providesPedidoUseCases(
        createPedidoUseCase: CreatePedidoUseCase,
        getPedidoByIdUseCase: GetPedidoByIdUseCase,
        listByUsuarioIdUseCase: ListByUsuarioIdUseCase,
        listByEntregaUseCase: ListByEntregaUseCase,
        listByEnviadoUseCase: ListByEnviadoUseCase
        ): PedidoUseCases = PedidoUseCases(
        createPedidoUseCase = createPedidoUseCase,
        getPedidoByIdUseCase = getPedidoByIdUseCase,
        listByUsuarioIdUseCase = listByUsuarioIdUseCase,
        listByEntregaUseCase = listByEntregaUseCase,
        listByEnviadoUseCase = listByEnviadoUseCase
    )
}