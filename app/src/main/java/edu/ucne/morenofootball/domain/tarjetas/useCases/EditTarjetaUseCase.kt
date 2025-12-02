package edu.ucne.morenofootball.domain.tarjetas.useCases

import edu.ucne.morenofootball.domain.tarjetas.TarjetaRepository
import edu.ucne.morenofootball.domain.tarjetas.models.Tarjeta
import edu.ucne.morenofootball.utils.Resource
import javax.inject.Inject

class EditTarjetaUseCase @Inject constructor(
    private val repo: TarjetaRepository
) {
    suspend operator fun invoke(tarjeta: Tarjeta): Resource<Tarjeta> {
        return when (val response = repo.edit(tarjeta)) {
            is Resource.Success -> Resource.Success(response.data ?: Tarjeta())
            is Resource.Error -> Resource.Error(response.message ?: "Hubo un error al editar la tarjeta")
            is Resource.Loading -> Resource.Loading()
        }
    }
}