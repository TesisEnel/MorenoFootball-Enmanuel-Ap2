package edu.ucne.morenofootball.data.entities.usuarios

import edu.ucne.morenofootball.data.entities.usuarios.local.UsuarioDao
import edu.ucne.morenofootball.data.entities.usuarios.remote.UsuarioRemoteDataSource
import edu.ucne.morenofootball.domain.usuarios.UsuarioRepository
import edu.ucne.morenofootball.domain.usuarios.models.Login
import edu.ucne.morenofootball.domain.usuarios.models.ModificarCredenciales
import edu.ucne.morenofootball.domain.usuarios.models.Register
import edu.ucne.morenofootball.utils.Resource
import javax.inject.Inject

class UsuarioRepositoryImpl @Inject constructor(
    private val remote: UsuarioRemoteDataSource,
    private val local: UsuarioDao,
) : UsuarioRepository {
    override suspend fun register(credenciales: Register): Resource<Unit> {
        val remoteResponse = remote.register(credenciales.toDto())
        return when (remoteResponse) {
            is Resource.Error -> Resource.Error(remoteResponse.message ?: "", Unit)
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> {
                val userResponse = remoteResponse.data
                local.save(userResponse!!.toEntity())
                Resource.Success(Unit)
            }
        }
    }

    override suspend fun login(credenciales: Login): Resource<Unit> {
        val remoteResponse = remote.login(credenciales.toDto())
        return when (remoteResponse) {
            is Resource.Error -> Resource.Error(remoteResponse.message ?: "", Unit)
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> {
                val userResponse = remoteResponse.data
                local.save(userResponse!!.toEntity())
                Resource.Success(Unit)
            }
        }
    }

    override suspend fun modificarCredenciales(credenciales: ModificarCredenciales): Resource<Unit> {
        val remoteResponse = remote.modificarCredenciales(credenciales.toDto())
        return when (remoteResponse) {
            is Resource.Error -> Resource.Error(remoteResponse.message ?: "", Unit)
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> {
                val userResponse = remoteResponse.data
                local.save(userResponse!!.toEntity())
                Resource.Success(Unit)
            }
        }
    }
}