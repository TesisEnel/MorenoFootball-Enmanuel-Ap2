package edu.ucne.morenofootball.domain.pedidos.useCases

import edu.ucne.morenofootball.domain.pedidos.PedidoRepository
import edu.ucne.morenofootball.domain.pedidos.models.response.PedidoResponse
import edu.ucne.morenofootball.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListByEnviadoUseCase @Inject constructor(
    private val repo: PedidoRepository
) {
    operator fun invoke(usuarioId: Int): Flow<Resource<List<PedidoResponse>>> = flow {
        emit(Resource.Loading())
        when (val response = repo.listByEnviado(usuarioId)) {
            is Resource.Success -> emit(Resource.Success(response.data ?: emptyList()))
            is Resource.Error -> emit(Resource.Error(response.message ?: ""))
            is Resource.Loading -> emit(Resource.Loading())
        }
    }
}