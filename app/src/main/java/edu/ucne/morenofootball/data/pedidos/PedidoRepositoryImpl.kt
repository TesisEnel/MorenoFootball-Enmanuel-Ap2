package edu.ucne.morenofootball.data.pedidos

import edu.ucne.morenofootball.data.pedidos.remote.PedidoRemoteDataSource
import edu.ucne.morenofootball.domain.pedidos.PedidoRepository
import edu.ucne.morenofootball.domain.pedidos.models.request.PedidoRequest
import edu.ucne.morenofootball.domain.pedidos.models.response.PedidoResponse
import edu.ucne.morenofootball.utils.Resource
import javax.inject.Inject

class PedidoRepositoryImpl @Inject constructor(
    private val remote: PedidoRemoteDataSource,
) : PedidoRepository {
    override suspend fun listByUsuarioId(usuarioId: Int): Resource<List<PedidoResponse>> {
        return when (val response = remote.listByUsuarioId(usuarioId)) {
            is Resource.Success -> Resource.Success(response.data?.map { it.toDomain() } ?: emptyList())
            is Resource.Error -> Resource.Error(response.message ?: "")
            is Resource.Loading -> Resource.Loading()
        }
    }

    override suspend fun getById(pedidoId: Int): Resource<PedidoResponse> {
        return when (val response = remote.getById(pedidoId)) {
            is Resource.Success -> Resource.Success(response.data?.toDomain() ?: PedidoResponse())
            is Resource.Error -> Resource.Error(response.message ?: "")
            is Resource.Loading -> Resource.Loading()
        }
    }

    override suspend fun listByEntrega(usuarioId: Int): Resource<List<PedidoResponse>> {
        return when (val response = remote.listByEntrega(usuarioId)) {
            is Resource.Success -> Resource.Success(response.data?.map { it.toDomain() } ?: emptyList())
            is Resource.Error -> Resource.Error(response.message ?: "")
            is Resource.Loading -> Resource.Loading()
        }
    }

    override suspend fun listByEnviado(usuarioId: Int): Resource<List<PedidoResponse>> {
        return when (val response = remote.listByEnviado(usuarioId)) {
            is Resource.Success -> Resource.Success(response.data?.map { it.toDomain() } ?: emptyList())
            is Resource.Error -> Resource.Error(response.message ?: "")
            is Resource.Loading -> Resource.Loading()
        }
    }

    override suspend fun create(request: PedidoRequest): Resource<Unit> {
        return when (val response = remote.create(request.toDto())) {
            is Resource.Success -> Resource.Success(Unit)
            is Resource.Error -> Resource.Error(response.message ?: "")
            is Resource.Loading -> Resource.Loading()
        }
    }
}