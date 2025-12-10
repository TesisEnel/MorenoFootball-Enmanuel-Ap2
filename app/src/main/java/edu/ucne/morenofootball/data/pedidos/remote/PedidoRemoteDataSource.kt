package edu.ucne.morenofootball.data.pedidos.remote

import edu.ucne.morenofootball.data.pedidos.remote.dto.request.PedidoRequestDto
import edu.ucne.morenofootball.data.pedidos.remote.dto.response.PedidoResponseDto
import edu.ucne.morenofootball.data.usuarios.remote.UsuarioRemoteDataSource
import edu.ucne.morenofootball.utils.Resource
import okio.IOException
import retrofit2.Response
import javax.inject.Inject

class PedidoRemoteDataSource @Inject constructor(
    private val api: PedidoApiService
) {
    private suspend fun <T, R> executeApiCall(
        apiCall: suspend (T) -> Response<R>,
        request: T,
        networkError: String = UsuarioRemoteDataSource.Companion.NETWORK_ERROR
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

    suspend fun listByUsuarioId(usuarioId: Int): Resource<List<PedidoResponseDto>> =
        executeApiCall(
            apiCall = { api.listByUsuarioId(it) },
            request = usuarioId
        )

    suspend fun getById(pedidoId: Int): Resource<PedidoResponseDto> =
        executeApiCall(
            apiCall = { api.getById(it) },
            request = pedidoId
        )

    suspend fun listByEntrega(usuarioId: Int): Resource<List<PedidoResponseDto>> =
        executeApiCall(
            apiCall = { api.listByEntrega(it) },
            request = usuarioId
        )

    suspend fun listByEnviado(usuarioId: Int): Resource<List<PedidoResponseDto>> =
        executeApiCall(
            apiCall = { api.listByEnviado(it) },
            request = usuarioId
        )

    suspend fun create(request: PedidoRequestDto): Resource<Unit> =
        executeApiCall(
            apiCall = { api.create(it) },
            request = request
        )
}