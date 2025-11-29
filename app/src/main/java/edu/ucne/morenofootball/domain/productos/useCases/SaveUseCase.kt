package edu.ucne.morenofootball.domain.productos.useCases

import edu.ucne.morenofootball.domain.productos.ProductoRepository
import edu.ucne.morenofootball.domain.productos.models.ProductoReq
import edu.ucne.morenofootball.utils.Resource
import javax.inject.Inject

class SaveUseCase @Inject constructor(
    private val repo: ProductoRepository
) {
    suspend operator fun invoke(producto: ProductoReq): Resource<Unit> {
        return when (val response = repo.save(producto)) {
            is Resource.Error -> Resource.Error(response.message ?: "")
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> Resource.Success(Unit)
        }
    }
}