package edu.ucne.morenofootball.data.tarjetas

import edu.ucne.morenofootball.data.tarjetas.remote.TarjetaRemoteDataSource
import edu.ucne.morenofootball.domain.tarjetas.TarjetaRepository
import edu.ucne.morenofootball.domain.tarjetas.models.Tarjeta
import edu.ucne.morenofootball.utils.Resource
import javax.inject.Inject

class TarjetaRepositorImpl @Inject constructor(
    private val remote: TarjetaRemoteDataSource
): TarjetaRepository {
    override suspend fun listByUsuarioId(usuarioId: Int): Resource<List<Tarjeta>> {
        return when (val response = remote.listByUsuarioId(usuarioId)) {
            is Resource.Success -> Resource.Success(response.data?.map { it.toDomain() } ?: emptyList())
            is Resource.Error -> Resource.Error(response.message ?: "Hubo un error al cargar las tarjetas")
            is Resource.Loading -> Resource.Loading()
        }
    }

    override suspend fun save(tarjeta: Tarjeta): Resource<Tarjeta> {
        return when (val response = remote.save(tarjeta.toDto())) {
            is Resource.Success -> Resource.Success(response.data?.toDomain() ?: Tarjeta())
            is Resource.Error -> Resource.Error(response.message ?: "Hubo un error al crear la tarjeta")
            is Resource.Loading -> Resource.Loading()
        }
    }

    override suspend fun edit(tarjeta: Tarjeta): Resource<Tarjeta> {
        return when (val response = remote.edit(tarjeta.toDto())) {
            is Resource.Success -> Resource.Success(response.data?.toDomain() ?: Tarjeta())
            is Resource.Error -> Resource.Error(response.message ?: "Hubo un error al modificar la tarjeta")
            is Resource.Loading -> Resource.Loading()
        }
    }

    override suspend fun delete(id: Int): Resource<Unit> {
        return when (val response = remote.delete(id)) {
            is Resource.Success -> Resource.Success(Unit)
            is Resource.Error -> Resource.Error(response.message ?: "Hubo un error al eliminar la tarjeta")
            is Resource.Loading -> Resource.Loading()
        }
    }
}