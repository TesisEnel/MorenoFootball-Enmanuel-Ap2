package edu.ucne.morenofootball.domain.pedidos.useCases

data class PedidoUseCases(
    val listByUsuarioIdUseCase: ListByUsuarioIdUseCase,
    val getPedidoByIdUseCase: GetPedidoByIdUseCase,
    val listByEntregaUseCase: ListByEntregaUseCase,
    val listByEnviadoUseCase: ListByEnviadoUseCase,
    val createPedidoUseCase: CreatePedidoUseCase
)
