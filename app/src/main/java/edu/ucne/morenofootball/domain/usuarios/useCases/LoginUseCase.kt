package edu.ucne.morenofootball.domain.usuarios.useCases

import edu.ucne.morenofootball.domain.usuarios.UsuarioRepository
import edu.ucne.morenofootball.domain.usuarios.models.Login
import edu.ucne.morenofootball.utils.Resource
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repo: UsuarioRepository
) {
    suspend operator fun invoke(credenciales: Login): Resource<Unit> {
        return when (val result = repo.login(credenciales)) {
            is Resource.Success -> Resource.Success(Unit)
            is Resource.Error -> Resource.Error(result.message ?: "")
            is Resource.Loading -> Resource.Loading()
        }
    }
}