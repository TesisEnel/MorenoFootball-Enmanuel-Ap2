package edu.ucne.morenofootball.domain.productos.useCases

data class ProductoUseCases(
    val listByAvailability: ListByAvailabilityUseCase,
    val listByTipo: ListByTipoUseCase,
    val save: SaveUseCase,
    val edit: EditUseCase,
    val delete: DeleteUseCase,
    val listByIds: ListByIdsUseCase
)
