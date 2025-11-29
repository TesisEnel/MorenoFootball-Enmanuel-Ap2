package edu.ucne.morenofootball.data.deseos.remote

import edu.ucne.morenofootball.data.deseos.remote.dto.request.AddProductRequest
import edu.ucne.morenofootball.data.deseos.remote.dto.request.DeseoRequest
import edu.ucne.morenofootball.data.deseos.remote.dto.response.DeseoResponse
import edu.ucne.morenofootball.data.usuarios.remote.UsuarioRemoteDataSource.Companion.NETWORK_ERROR
import edu.ucne.morenofootball.utils.Resource
import okio.IOException
import retrofit2.Response
import javax.inject.Inject

class DeseoRemoteDataSource @Inject constructor(
    private val api: DeseoApiService
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

    suspend fun listByUsuarioId(usuarioId: Int): Resource<List<DeseoResponse>> =
        executeApiCall(
            apiCall = { api.listByUsuarioId(it) },
            request = usuarioId,
        )

    suspend fun create(request: DeseoRequest): Resource<DeseoResponse> =
        executeApiCall(
            apiCall = { api.create(it) },
            request = request,
        )

    suspend fun addProduct(request: AddProductRequest): Resource<DeseoResponse> =
        executeApiCall(
            apiCall = { api.addProduct(it) },
            request = request,
        )

    suspend fun delete(deseoId: Int): Resource<Unit> =
        executeApiCall(
            apiCall = { api.delete(it) },
            request = deseoId,
        )

    suspend fun deleteProduct(detalleId: Int): Resource<Unit> =
        executeApiCall(
            apiCall = { api.deleteProduct(it) },
            request = detalleId,
        )
}