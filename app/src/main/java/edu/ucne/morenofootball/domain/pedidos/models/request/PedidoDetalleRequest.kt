package edu.ucne.morenofootball.domain.pedidos.models.request

data class PedidoDetalleRequest(
    val detalleId: Int = 0,
    val pedidoId: Int = 0,
    val productoId: Int = 0,
    val cantidad: Int = 0,
)
