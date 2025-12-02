package edu.ucne.morenofootball.domain.tarjetas.useCases

import edu.ucne.morenofootball.domain.tarjetas.TarjetaRepository
import edu.ucne.morenofootball.utils.Resource
import javax.inject.Inject

class DeleteTarjetaUseCase @Inject constructor(
    private val repo: TarjetaRepository
) {
    suspend operator fun invoke(id: Int): Resource<Unit> {
        return when (val response = repo.delete(id)) {
            is Resource.Success -> Resource.Success(Unit)
            is Resource.Error -> Resource.Error(response.message ?: "Hubo un error al eliminar la tarjeta")
            is Resource.Loading -> Resource.Loading()
        }
    }
}