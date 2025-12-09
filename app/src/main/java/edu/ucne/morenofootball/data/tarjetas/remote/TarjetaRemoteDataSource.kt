package edu.ucne.morenofootball.data.tarjetas.remote

import edu.ucne.morenofootball.data.tarjetas.remote.dto.request.EditTarjetaRequest
import edu.ucne.morenofootball.data.tarjetas.remote.dto.request.TarjetaRequest
import edu.ucne.morenofootball.data.tarjetas.remote.dto.response.TarjetaResponse
import edu.ucne.morenofootball.data.usuarios.remote.UsuarioRemoteDataSource.Companion.NETWORK_ERROR
import edu.ucne.morenofootball.utils.Resource
import okio.IOException
import retrofit2.Response
import javax.inject.Inject

class TarjetaRemoteDataSource @Inject constructor(
    private val api: TarjetaApiService
) {
    private suspend fun <T, R> executeApiCall(
        apiCall: suspend (T) -> Response<R>,
        request: T,
        networkError: String = NETWORK_ERROR
    ): Resource<R> =
        try {
            val response = apiCall(request)
            if (response.isSuccessful)
                response.body()?.let { Resource.Success(it) }
                    ?: Resource.Error("Respuesta vacía del servidor")
            else Resource.Error(response.message())
        } catch (e: IOException) {
            Resource.Error(networkError)
        }

    suspend fun listByUsuarioId(usuarioId: Int): Resource<List<TarjetaResponse>> =
        executeApiCall(
            apiCall = { api.listByUsuarioId(it) },
            request = usuarioId,
        )

    suspend fun getByTarjetaId(tarjetaId: Int): Resource<TarjetaResponse> =
        executeApiCall(
            apiCall = { api.getByTarjetaId(it) },
            request = tarjetaId,
        )

    suspend fun save(request: TarjetaRequest): Resource<TarjetaResponse> =
        executeApiCall(
            apiCall = { api.create(it) },
            request = request,
        )
    suspend fun edit(request: EditTarjetaRequest): Resource<TarjetaResponse> =
        executeApiCall(
            apiCall = { api.edit(it) },
            request = request
        )

    suspend fun delete(tarjetaId: Int): Resource<Unit> =
        executeApiCall(
            apiCall = { api.delete(tarjetaId) },
            request = Unit
        )
}
