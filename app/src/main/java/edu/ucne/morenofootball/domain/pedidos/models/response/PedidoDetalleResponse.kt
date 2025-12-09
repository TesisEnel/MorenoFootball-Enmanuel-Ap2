package edu.ucne.morenofootball.domain.pedidos.models.response

data class PedidoDetalleResponse(
    val detalleId: Int,
    val pedidoId: Int,
    val productoId: Int,
    val cantidad: Int,
)
