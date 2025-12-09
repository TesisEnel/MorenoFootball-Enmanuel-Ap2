package edu.ucne.morenofootball.domain.carritos.useCases

import edu.ucne.morenofootball.domain.carritos.CarritoRepository
import edu.ucne.morenofootball.domain.carritos.models.request.AgregarProductoParams
import edu.ucne.morenofootball.domain.carritos.models.response.CarritoResponse
import edu.ucne.morenofootball.utils.Resource
import javax.inject.Inject

class AgregarProductoUseCase @Inject constructor(
    private val repo: CarritoRepository
){
    suspend operator fun invoke(params: AgregarProductoParams): Resource<Unit> {
        return when(val result = repo.agregarProducto(params)) {
            is Resource.Error -> Resource.Error(result.message ?: "")
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> Resource.Success(Unit)
        }
    }
}