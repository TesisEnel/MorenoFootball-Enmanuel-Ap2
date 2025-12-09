package edu.ucne.morenofootball.data.pedidos.remote.dto.response

data class PedidoDetalleResponseDto(
    val detalleId: Int,
    val pedidoId: Int,
    val productoId: Int,
    val cantidad: Int,
)
