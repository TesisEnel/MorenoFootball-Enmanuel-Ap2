package edu.ucne.morenofootball.domain.pedidos

import edu.ucne.morenofootball.domain.pedidos.models.request.PedidoRequest
import edu.ucne.morenofootball.domain.pedidos.models.response.PedidoResponse
import edu.ucne.morenofootball.utils.Resource

interface PedidoRepository {
    suspend fun listByUsuarioId(usuarioId: Int): Resource<List<PedidoResponse>>
    suspend fun getById(pedidoId: Int): Resource<PedidoResponse>
    suspend fun listByEntrega(usuarioId: Int): Resource<List<PedidoResponse>>
    suspend fun listByEnviado(usuarioId: Int): Resource<List<PedidoResponse>>
    suspend fun create(request: PedidoRequest): Resource<Unit>
}