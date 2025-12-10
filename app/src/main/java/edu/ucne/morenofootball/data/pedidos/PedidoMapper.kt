package edu.ucne.morenofootball.data.pedidos

import edu.ucne.morenofootball.data.pedidos.remote.dto.request.PedidoDetalleRequestDto
import edu.ucne.morenofootball.data.pedidos.remote.dto.request.PedidoRequestDto
import edu.ucne.morenofootball.data.pedidos.remote.dto.response.PedidoDetalleResponseDto
import edu.ucne.morenofootball.data.pedidos.remote.dto.response.PedidoResponseDto
import edu.ucne.morenofootball.domain.pedidos.models.request.PedidoDetalleRequest
import edu.ucne.morenofootball.domain.pedidos.models.request.PedidoRequest
import edu.ucne.morenofootball.domain.pedidos.models.response.PedidoDetalleResponse
import edu.ucne.morenofootball.domain.pedidos.models.response.PedidoResponse

fun PedidoResponseDto.toDomain() = PedidoResponse(
    pedidoId,
    usuarioId,
    tarjetaId,
    fechaPedido,
    fechaEnviado,
    fechaEntrega,
    estaEntregado,
    estaEnviado,
    detalle.map { it.toDomain() }
)

fun PedidoDetalleResponseDto.toDomain() = PedidoDetalleResponse(
    detalleId,
    pedidoId,
    productoId,
    cantidad,
)

fun PedidoRequest.toDto() = PedidoRequestDto(
    usuarioId,
    tarjetaId,
    detalles.map { it.toDto() }
)

fun PedidoDetalleRequest.toDto() = PedidoDetalleRequestDto(
    productoId,
    cantidad,
)