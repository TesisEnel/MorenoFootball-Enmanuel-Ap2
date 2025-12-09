package edu.ucne.morenofootball.domain.carritos.useCases

import edu.ucne.morenofootball.domain.carritos.CarritoRepository
import edu.ucne.morenofootball.domain.carritos.models.request.ActionWithProductFromCardParams
import edu.ucne.morenofootball.utils.Resource
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    private val repo: CarritoRepository
){
    suspend operator fun invoke(params: ActionWithProductFromCardParams): Resource<Unit> {
        return when(val result = repo.deleteProduct(params)) {
            is Resource.Error -> Resource.Error(result.message ?: "")
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> Resource.Success(Unit)
        }
    }
}