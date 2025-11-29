package edu.ucne.morenofootball.domain.productos.useCases

import edu.ucne.morenofootball.domain.productos.ProductoRepository
import edu.ucne.morenofootball.domain.productos.models.Producto
import edu.ucne.morenofootball.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListByTipoUseCase @Inject constructor(
    private val repo: ProductoRepository
) {
    suspend operator fun invoke(tipoProducto: Int): Flow<Resource<List<Producto>>> = flow {
        emit(Resource.Loading())
        when (val response = repo.listByTipo(tipoProducto)) {
            is Resource.Error -> emit(Resource.Error(response.message ?: ""))
            is Resource.Loading -> emit(Resource.Loading())
            is Resource.Success -> emit(Resource.Success(response.data ?: emptyList()))
        }
    }
}