package edu.ucne.morenofootball.data.carritos.remote

import edu.ucne.morenofootball.data.carritos.remote.dto.request.AgregarProductoParams
import edu.ucne.morenofootball.data.carritos.remote.dto.request.ModificarCantidadParams
import edu.ucne.morenofootball.data.carritos.remote.dto.response.CarritoResponse
import edu.ucne.morenofootball.data.usuarios.remote.UsuarioRemoteDataSource.Companion.NETWORK_ERROR
import edu.ucne.morenofootball.utils.Resource
import okio.IOException
import retrofit2.Response
import javax.inject.Inject

class CarritoRemoteDataSource @Inject constructor(
    private val api: CarritoApiService
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

    suspend fun listByUsuarioId(): Resource<List<CarritoResponse>> =
        executeApiCall(
            apiCall = { api.listByUsuarioId() },
            request = Unit,
        )
    suspend fun getTotalCarrito(): Resource<Double> =
        executeApiCall(
            apiCall = { api.getTotalCarrito() },
            request = Unit,
        )
    suspend fun agregarProducto(params: AgregarProductoParams): Resource<CarritoResponse> =
        executeApiCall(
            apiCall = { api.agregarProducto(it) },
            request = params
        )
    suspend fun aumentarCantidad(params: ModificarCantidadParams): Resource<CarritoResponse> =
        executeApiCall(
            apiCall = { api.aumentarCantidad(it) },
            request = params
        )
    suspend fun disminuirCantidad(params: ModificarCantidadParams): Resource<CarritoResponse> =
        executeApiCall(
            apiCall = { api.disminuirCantidad(it) },
            request = params
        )

    suspend fun vaciarCarrito(): Resource<Unit> =
        executeApiCall(
            apiCall = { api.vaciarCarrito() },
            request = Unit
        )
}
