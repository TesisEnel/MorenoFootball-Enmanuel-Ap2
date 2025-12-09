package edu.ucne.morenofootball.domain.productos.useCases

import edu.ucne.morenofootball.domain.productos.ProductoRepository
import edu.ucne.morenofootball.domain.productos.models.Producto
import edu.ucne.morenofootball.utils.Resource
import javax.inject.Inject

class GetByIdUseCase @Inject constructor(
    private val repo: ProductoRepository
) {
    suspend operator fun invoke(productoId: Int): Resource<Producto> {
        return when(val result = repo.getById(productoId)) {
            is Resource.Error -> Resource.Error(result.message ?: "Error desconocido")
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> Resource.Success(result.data ?: Producto())
        }
    }
}