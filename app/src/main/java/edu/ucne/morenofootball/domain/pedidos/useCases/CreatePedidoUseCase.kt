package edu.ucne.morenofootball.domain.pedidos.useCases

import edu.ucne.morenofootball.data.pedidos.remote.dto.response.PedidoResponseDto
import edu.ucne.morenofootball.domain.pedidos.PedidoRepository
import edu.ucne.morenofootball.domain.pedidos.models.request.PedidoRequest
import edu.ucne.morenofootball.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreatePedidoUseCase (
    private val repo: PedidoRepository
) {
    suspend operator fun invoke(request: PedidoRequest): Resource<Unit> {
        return when (val response = repo.create(request)) {
            is Resource.Success -> (Resource.Success(Unit))
            is Resource.Error -> Resource.Error(response.message ?: "")
            is Resource.Loading -> Resource.Loading()
        }
    }
}