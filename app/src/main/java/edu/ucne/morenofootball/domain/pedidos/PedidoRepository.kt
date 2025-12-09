package edu.ucne.morenofootball.domain.pedidos

import edu.ucne.morenofootball.data.pedidos.remote.dto.response.PedidoResponseDto
import edu.ucne.morenofootball.domain.pedidos.models.request.PedidoRequest
import edu.ucne.morenofootball.utils.Resource

interface PedidoRepository {
    suspend fun listByUsuarioId(usuarioId: Int): Resource<List<PedidoResponseDto>>
    suspend fun listByEntrega(usuarioId: Int): Resource<List<PedidoResponseDto>>
    suspend fun listByEnviado(usuarioId: Int): Resource<List<PedidoResponseDto>>
    suspend fun create(request: PedidoRequest): Resource<Unit>
}