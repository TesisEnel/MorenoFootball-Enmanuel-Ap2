package edu.ucne.morenofootball.domain.pedidos.models.response

data class PedidoResponse(
    val pedidoId: Int = 0,
    val usuarioId: Int = 0,
    val tarjetaId: Int = 0,
    val fechaPedido: String = "",
    val fechaEnviado: String = "",
    val fechaEntrega: String = "",
    val estaEntregado: Boolean = false,
    val estaEnviado: Boolean = false,
    val detalles: List<PedidoDetalleResponse> = emptyList()
)
