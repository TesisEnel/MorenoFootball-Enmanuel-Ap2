package edu.ucne.morenofootball.domain.usuarios.useCases

import edu.ucne.morenofootball.domain.usuarios.UsuarioRepository
import edu.ucne.morenofootball.domain.usuarios.models.ModificarCredenciales
import edu.ucne.morenofootball.utils.Resource
import javax.inject.Inject

class ModificarCredencialesUseCase @Inject constructor(
    private val repo: UsuarioRepository,
) {
    suspend operator fun invoke(credenciales: ModificarCredenciales): Resource<Unit> {
        return when(val result = repo.modificarCredenciales(credenciales)){
            is Resource.Success -> Resource.Success(Unit)
            is Resource.Error -> Resource.Error(result.message ?: "")
            is Resource.Loading -> Resource.Loading()
        }
    }
}