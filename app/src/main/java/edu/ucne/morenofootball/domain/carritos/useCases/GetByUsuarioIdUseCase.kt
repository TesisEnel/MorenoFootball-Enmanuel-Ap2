package edu.ucne.morenofootball.domain.carritos.useCases

import edu.ucne.morenofootball.domain.carritos.CarritoRepository
import edu.ucne.morenofootball.domain.carritos.models.request.UserOrSessionIdParams
import edu.ucne.morenofootball.domain.carritos.models.response.CarritoResponse
import edu.ucne.morenofootball.utils.Resource
import javax.inject.Inject

class GetByUsuarioIdUseCase @Inject constructor(
    private val repo: CarritoRepository
){
    suspend operator fun invoke(params: UserOrSessionIdParams): Resource<CarritoResponse> {
        return when(val result = repo.getByUsuarioId(params)) {
            is Resource.Error -> Resource.Error(result.message ?: "")
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> Resource.Success(result.data ?: CarritoResponse())
        }
    }
}