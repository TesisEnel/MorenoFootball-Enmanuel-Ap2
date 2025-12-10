package edu.ucne.morenofootball.data.pedidos.remote.dto.request

data class PedidoRequestDto (
    val usuarioId: Int,
    val tarjetaId: Int,
    val detalle: List<PedidoDetalleRequestDto>
)