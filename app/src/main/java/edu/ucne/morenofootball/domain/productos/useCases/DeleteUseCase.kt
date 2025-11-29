package edu.ucne.morenofootball.domain.productos.useCases

import edu.ucne.morenofootball.domain.productos.ProductoRepository
import edu.ucne.morenofootball.utils.Resource
import javax.inject.Inject

class DeleteUseCase @Inject constructor(
    private val repo: ProductoRepository
) {
    suspend operator fun invoke(productoId: Int): Resource<Unit> {
        return when (val response = repo.delete(productoId)) {
            is Resource.Error -> Resource.Error(response.message ?: "")
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> Resource.Success(Unit)
        }
    }
}