package edu.ucne.morenofootball.data.pedidos

import edu.ucne.morenofootball.data.pedidos.remote.PedidoRemoteDataSource
import edu.ucne.morenofootball.data.pedidos.remote.dto.request.PedidoRequestDto
import edu.ucne.morenofootball.data.pedidos.remote.dto.response.PedidoResponseDto
import edu.ucne.morenofootball.domain.pedidos.PedidoRepository
import edu.ucne.morenofootball.domain.pedidos.models.request.PedidoRequest
import edu.ucne.morenofootball.utils.Resource
import javax.inject.Inject

class PedidoRepositoryImpl @Inject constructor(
    private val remote: PedidoRemoteDataSource,
) : PedidoRepository {
    override suspend fun listByUsuarioId(usuarioId: Int): Resource<List<PedidoResponseDto>> {
        return when (val response = remote.listByUsuarioId(usuarioId)) {
            is Resource.Success -> Resource.Success(response.data ?: emptyList())
            is Resource.Error -> Resource.Error(response.message ?: "")
            is Resource.Loading -> Resource.Loading()
        }
    }

    override suspend fun listByEntrega(usuarioId: Int): Resource<List<PedidoResponseDto>> {
        return when (val response = remote.listByEntrega(usuarioId)) {
            is Resource.Success -> Resource.Success(response.data ?: emptyList())
            is Resource.Error -> Resource.Error(response.message ?: "")
            is Resource.Loading -> Resource.Loading()
        }
    }

    override suspend fun listByEnviado(usuarioId: Int): Resource<List<PedidoResponseDto>> {
        return when (val response = remote.listByEnviado(usuarioId)) {
            is Resource.Success -> Resource.Success(response.data ?: emptyList())
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