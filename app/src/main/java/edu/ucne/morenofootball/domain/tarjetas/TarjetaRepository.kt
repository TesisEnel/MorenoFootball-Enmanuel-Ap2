package edu.ucne.morenofootball.domain.tarjetas

import edu.ucne.morenofootball.domain.tarjetas.models.Tarjeta
import edu.ucne.morenofootball.utils.Resource

interface TarjetaRepository {
    suspend fun listByUsuarioId(usuarioId: Int): Resource<List<Tarjeta>>
    suspend fun save(tarjeta: Tarjeta): Resource<Tarjeta>
    suspend fun edit(tarjeta: Tarjeta): Resource<Tarjeta>
    suspend fun delete(id: Int): Resource<Unit>
}