package edu.ucne.morenofootball.domain.tarjetas.useCases

data class TarjetaUseCases(
    val listTarjetasByUsuarioIdUsecase: ListTarjetasByUsuarioIdUsecase,
    val saveTarjetaUseCase: SaveTarjetaUseCase,
    val editTarjetaUseCase: EditTarjetaUseCase,
    val deleteTarjetaUseCase: DeleteTarjetaUseCase
)