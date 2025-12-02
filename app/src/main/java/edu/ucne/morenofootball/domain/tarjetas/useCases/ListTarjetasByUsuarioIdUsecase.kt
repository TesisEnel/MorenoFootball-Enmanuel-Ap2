package edu.ucne.morenofootball.domain.tarjetas.useCases

import edu.ucne.morenofootball.domain.tarjetas.TarjetaRepository
import edu.ucne.morenofootball.domain.tarjetas.models.Tarjeta
import edu.ucne.morenofootball.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListTarjetasByUsuarioIdUsecase @Inject constructor(
    private val repo: TarjetaRepository
) {
    operator fun invoke(usuarioId: Int): Flow<Resource<List<Tarjeta>>> = flow {
        emit(Resource.Loading())
        when (val response = repo.listByUsuarioId(usuarioId)) {
            is Resource.Success -> emit(Resource.Success(response.data ?: emptyList()))
            is Resource.Error -> emit(Resource.Error(response.message ?: "Hubo un error al cargar las tarjetas"))
            is Resource.Loading -> emit(Resource.Loading())
        }
    }
}
