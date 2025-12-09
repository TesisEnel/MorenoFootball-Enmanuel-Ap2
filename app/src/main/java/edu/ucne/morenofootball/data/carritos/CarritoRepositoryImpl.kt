package edu.ucne.morenofootball.data.carritos

import edu.ucne.morenofootball.data.carritos.remote.CarritoRemoteDataSource
import edu.ucne.morenofootball.domain.carritos.CarritoRepository
import edu.ucne.morenofootball.domain.carritos.models.request.ActionWithProductFromCardParams
import edu.ucne.morenofootball.domain.carritos.models.request.AgregarProductoParams
import edu.ucne.morenofootball.domain.carritos.models.request.UserOrSessionIdParams
import edu.ucne.morenofootball.domain.carritos.models.response.CarritoResponse
import edu.ucne.morenofootball.utils.Resource
import javax.inject.Inject

class CarritoRepositoryImpl @Inject constructor(
    private val remote: CarritoRemoteDataSource
): CarritoRepository {
    override suspend fun getByUsuarioId(params: UserOrSessionIdParams): Resource<CarritoResponse> {
        return when(val response = remote.getByUsuarioId(params.toDto())) {
            is Resource.Success -> Resource.Success(response.data?.toDomain() ?: CarritoResponse())
            is Resource.Error -> Resource.Error(response.message ?: "Error desconocido")
            is Resource.Loading -> Resource.Loading()
        }
    }

    override suspend fun getTotalCarrito(params: UserOrSessionIdParams): Resource<Double> {
        return when(val response = remote.getTotalCarrito(params.toDto())) {
            is Resource.Success -> Resource.Success(response.data ?: 0.0)
            is Resource.Error -> Resource.Error(response.message ?: "Error desconocido")
            is Resource.Loading -> Resource.Loading()
        }
    }

    override suspend fun agregarProducto(params: AgregarProductoParams): Resource<Unit> {
        return when(val response = remote.agregarProducto(params.toDto())) {
            is Resource.Success -> Resource.Success(Unit)
            is Resource.Error -> Resource.Error(response.message ?: "Error desconocido")
            is Resource.Loading -> Resource.Loading()
        }
    }

    override suspend fun aumentarCantidad(params: ActionWithProductFromCardParams): Resource<Unit> {
        return when(val response = remote.aumentarCantidad(params.toDto())) {
            is Resource.Success -> Resource.Success(Unit)
            is Resource.Error -> Resource.Error(response.message ?: "Error desconocido")
            is Resource.Loading -> Resource.Loading()
        }
    }

    override suspend fun disminuirCantidad(params: ActionWithProductFromCardParams): Resource<Unit> {
        return when(val response = remote.disminuirCantidad(params.toDto())) {
            is Resource.Success -> Resource.Success(Unit)
            is Resource.Error -> Resource.Error(response.message ?: "Error desconocido")
            is Resource.Loading -> Resource.Loading()
        }
    }

    override suspend fun vaciarCarrito(params: UserOrSessionIdParams): Resource<Unit> {
        return when(val response = remote.vaciarCarrito(params.toDto())) {
            is Resource.Success -> Resource.Success(Unit)
            is Resource.Error -> Resource.Error(response.message ?: "Error desconocido")
            is Resource.Loading -> Resource.Loading()
        }
    }

    override suspend fun deleteProduct(params: ActionWithProductFromCardParams): Resource<Unit> {
        return when(val response = remote.deleteProduct(params.toDto())) {
            is Resource.Success -> Resource.Success(Unit)
            is Resource.Error -> Resource.Error(response.message ?: "Error desconocido")
            is Resource.Loading -> Resource.Loading()
        }
    }
}