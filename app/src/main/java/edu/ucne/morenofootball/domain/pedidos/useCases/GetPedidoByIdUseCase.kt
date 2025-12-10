package edu.ucne.morenofootball.domain.pedidos.useCases

import edu.ucne.morenofootball.domain.pedidos.PedidoRepository
import edu.ucne.morenofootball.domain.pedidos.models.response.PedidoResponse
import edu.ucne.morenofootball.utils.Resource
import javax.inject.Inject

class GetPedidoByIdUseCase @Inject constructor(
    private val repo: PedidoRepository
) {
    suspend operator fun invoke(pedidoId: Int): Resource<PedidoResponse> {
        return when (val response = repo.getById(pedidoId)) {
            is Resource.Success -> Resource.Success(response.data ?: PedidoResponse())
            is Resource.Error -> Resource.Error(response.message ?: "")
            is Resource.Loading -> Resource.Loading()
        }
    }
}