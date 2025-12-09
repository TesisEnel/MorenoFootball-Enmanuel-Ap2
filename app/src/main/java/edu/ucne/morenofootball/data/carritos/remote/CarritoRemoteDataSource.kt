package edu.ucne.morenofootball.data.carritos.remote

import edu.ucne.morenofootball.data.carritos.remote.dto.request.AgregarProductoParamsReqDto
import edu.ucne.morenofootball.data.carritos.remote.dto.request.ActionWithProductFromCardParamsReqDto
import edu.ucne.morenofootball.data.carritos.remote.dto.request.UserOrSessionIdParamsReqDto
import edu.ucne.morenofootball.data.carritos.remote.dto.response.CarritoResponseResDto
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

    suspend fun getByUsuarioId(params: UserOrSessionIdParamsReqDto): Resource<CarritoResponseResDto> =
        executeApiCall(
            apiCall = { api.getByUsuarioId(it.usuarioId ?: 0, it.sesionAnonimaId) },
            request = params,
        )
    suspend fun getTotalCarrito(params: UserOrSessionIdParamsReqDto): Resource<Double> =
        executeApiCall(
            apiCall = { api.getTotalCarrito(it.usuarioId ?: 0, it.sesionAnonimaId) },
            request = params,
        )
    suspend fun agregarProducto(params: AgregarProductoParamsReqDto): Resource<Unit> =
        executeApiCall(
            apiCall = { api.agregarProducto(it.usuarioId ?: 0, it.productoId, it.cantidad, it.sesionAnonimaId) },
            request = params
        )
    suspend fun aumentarCantidad(params: ActionWithProductFromCardParamsReqDto): Resource<Unit> =
        executeApiCall(
            apiCall = { api.aumentarCantidad(it.usuarioId ?: 0, it.productoId, it.sesionAnonimaId) },
            request = params
        )
    suspend fun disminuirCantidad(params: ActionWithProductFromCardParamsReqDto): Resource<Unit> =
        executeApiCall(
            apiCall = { api.disminuirCantidad(it.usuarioId ?: 0, it.productoId, it.sesionAnonimaId) },
            request = params
        )

    suspend fun vaciarCarrito(params: UserOrSessionIdParamsReqDto): Resource<Unit> =
        executeApiCall(
            apiCall = { api.vaciarCarrito(it.usuarioId ?: 0, it.sesionAnonimaId) },
            request = params
        )

    suspend fun deleteProduct(params: ActionWithProductFromCardParamsReqDto): Resource<Unit> =
        executeApiCall(
            apiCall = { api.deleteProduct(it.usuarioId ?: 0, it.productoId, it.sesionAnonimaId) },
            request = params
        )
}
