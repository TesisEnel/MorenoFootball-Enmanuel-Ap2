package edu.ucne.morenofootball.data.pedidos.remote.dto.response

data class PedidoResponseDto(
    val pedidoId: Int,
    val usuarioId: Int,
    val tarjetaId: Int,
    val fechaPedido: String,
    val fechaEnviado: String,
    val fechaEntrega: String,
    val estaEntregado: Boolean,
    val estaEnviado: Boolean,
    val detalles: List<PedidoDetalleResponseDto>
)
