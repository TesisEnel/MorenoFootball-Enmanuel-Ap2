package edu.ucne.morenofootball.data.productos

import edu.ucne.morenofootball.data.productos.remote.ProductoRemoteDataSource
import edu.ucne.morenofootball.domain.productos.ProductoRepository
import edu.ucne.morenofootball.domain.productos.models.*
import edu.ucne.morenofootball.utils.Resource
import javax.inject.Inject

class ProductoRepositoryImpl @Inject constructor(
    private val remote: ProductoRemoteDataSource
): ProductoRepository {
    override suspend fun listByAvability(): Resource<List<Producto>> {
        return when (val response = remote.listByAvability()) {
            is Resource.Error -> Resource.Error(response.message ?: "")
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> Resource.Success(response.data?.map { it.toDomain() } ?: emptyList())
        }
    }

    override suspend fun getById(productoId: Int): Resource<Producto> {
        return when (val response = remote.getById(productoId)) {
            is Resource.Error -> Resource.Error(response.message ?: "")
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> Resource.Success(response.data?.toDomain() ?: Producto())
        }    }

    override suspend fun listByIds(productosIds: List<Int>): Resource<List<Producto>> {
        return when (val response = remote.listByIds(productosIds)) {
            is Resource.Error -> Resource.Error(response.message ?: "")
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> Resource.Success(response.data?.map { it.toDomain() } ?: emptyList())
        }    }

    override suspend fun listByTipo(tipoProducto: Int): Resource<List<Producto>> {
        return when (val response = remote.listByTipo(tipoProducto)) {
            is Resource.Error -> Resource.Error(response.message ?: "")
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> Resource.Success(response.data?.map { it.toDomain() } ?: emptyList())
        }
    }

    override suspend fun save(producto: ProductoReq): Resource<Unit> {
        return when (val response = remote.save(producto.toDto())) {
            is Resource.Error -> Resource.Error(response.message ?: "")
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> Resource.Success(Unit)
        }
    }

    override suspend fun edit(productoEdit: ProductoReqEdit): Resource<Unit> {
        return when (val response = remote.edit(productoEdit.toDto())) {
            is Resource.Error -> Resource.Error(response.message ?: "")
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> Resource.Success(Unit)
        }
    }

    override suspend fun delete(productoId: Int): Resource<Unit> {
        return when (val response = remote.delete(productoId)) {
            is Resource.Error -> Resource.Error(response.message ?: "")
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> Resource.Success(Unit)
        }
    }

}