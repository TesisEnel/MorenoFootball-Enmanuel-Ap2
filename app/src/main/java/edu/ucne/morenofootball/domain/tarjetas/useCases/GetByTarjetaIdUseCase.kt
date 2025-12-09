package edu.ucne.morenofootball.domain.tarjetas.useCases

import edu.ucne.morenofootball.domain.tarjetas.TarjetaRepository
import edu.ucne.morenofootball.domain.tarjetas.models.Tarjeta
import edu.ucne.morenofootball.utils.Resource
import javax.inject.Inject

class GetByTarjetaIdUseCase @Inject constructor(
    private val repo: TarjetaRepository
) {
    suspend operator fun invoke(tarjetaId: Int): Resource<Tarjeta> {
        return when (val response = repo.getByTarjetaId(tarjetaId)) {
            is Resource.Success -> Resource.Success(response.data ?: Tarjeta())
            is Resource.Error -> Resource.Error(response.message ?: "Hubo un error al cargar la tarjeta")
            is Resource.Loading -> Resource.Loading()
        }
    }
}