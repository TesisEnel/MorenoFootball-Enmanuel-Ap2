package edu.ucne.morenofootball.data.productos.remote

import edu.ucne.morenofootball.data.productos.remote.dto.request.ProductoRequest
import edu.ucne.morenofootball.data.productos.remote.dto.request.ProductoRequestEdit
import edu.ucne.morenofootball.data.productos.remote.dto.response.ProductoResponse
import edu.ucne.morenofootball.data.usuarios.remote.UsuarioRemoteDataSource.Companion.NETWORK_ERROR
import edu.ucne.morenofootball.utils.Resource
import okio.IOException
import retrofit2.Response
import javax.inject.Inject

class ProductoRemoteDataSource @Inject constructor(
    private val api: ProductoApiService
){
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

    suspend fun listByAvability(): Resource<List<ProductoResponse>> =
        executeApiCall(
            apiCall = { api.listByAvability() },
            request = Unit,
        )

    suspend fun listByTipo(tipoProducto: Int): Resource<List<ProductoResponse>> =
        executeApiCall(
            apiCall = { api.listByTipo(it) },
            request = tipoProducto
        )

    suspend fun save(request: ProductoRequest): Resource<ProductoResponse> =
        executeApiCall(
            apiCall = { api.save(it) },
            request = request
        )

    suspend fun edit(request: ProductoRequestEdit): Resource<ProductoResponse> =
        executeApiCall(
            apiCall = { api.edit(it) },
            request = request
        )

    suspend fun delete(productoId: Int): Resource<Unit> =
        executeApiCall(
            apiCall = { api.delete(it) },
            request = productoId
        )
}