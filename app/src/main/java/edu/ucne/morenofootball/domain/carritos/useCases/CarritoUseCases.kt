package edu.ucne.morenofootball.domain.carritos.useCases

data class CarritoUseCases(
    val getByUsuarioIdUseCase: GetByUsuarioIdUseCase,
    val getTotalCarritoUseCase: GetTotalCarritoUseCase,
    val agregarProductoUseCase: AgregarProductoUseCase,
    val aumentarCantidadUseCase: AumentarCantidadUseCase,
    val disminuirCantidadUseCase: DisminuirCantidadUseCase,
    val vaciarCarritoUseCase: VaciarCarritoUseCase,
    val deleteProductUseCase: DeleteProductUseCase
)
