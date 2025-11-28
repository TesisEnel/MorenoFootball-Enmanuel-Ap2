package edu.ucne.morenofootball.data.usuarios

import edu.ucne.morenofootball.data.usuarios.local.UsuarioDao
import edu.ucne.morenofootball.data.usuarios.remote.UsuarioRemoteDataSource
import edu.ucne.morenofootball.domain.usuarios.UsuarioRepository
import edu.ucne.morenofootball.domain.usuarios.models.Login
import edu.ucne.morenofootball.domain.usuarios.models.ModificarCredenciales
import edu.ucne.morenofootball.domain.usuarios.models.Register
import edu.ucne.morenofootball.domain.usuarios.models.Usuario
import edu.ucne.morenofootball.utils.Resource
import javax.inject.Inject

class UsuarioRepositoryImpl @Inject constructor(
    private val remote: UsuarioRemoteDataSource,
    private val local: UsuarioDao,
) : UsuarioRepository {
    override suspend fun register(credenciales: Register): Resource<Unit> {
        return when (val remoteResponse = remote.register(credenciales.toDto())) {
            is Resource.Error -> Resource.Error(remoteResponse.message ?: "", Unit)
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> {
                remoteResponse.data?.let {
                    local.save(it.toEntity(true))
                    return Resource.Success(Unit)
                } ?: Resource.Error(remoteResponse.message ?: "Error al registrarse")
            }
        }
    }

    override suspend fun login(credenciales: Login): Resource<Unit> {
        return when (val remoteResponse = remote.login(credenciales.toDto())) {
            is Resource.Error -> Resource.Error(remoteResponse.message ?: "", Unit)
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> {
                remoteResponse.data?.let {
                    val localUser = it.toEntity(credenciales.rememberUser)
                    local.save(localUser)
                    return Resource.Success(Unit)
                } ?: Resource.Error(remoteResponse.message ?: "Error al iniciar sesión")
            }
        }
    }

    override suspend fun modificarCredenciales(credenciales: ModificarCredenciales): Resource<Unit> {
        return when (val remoteResponse = remote.modificarCredenciales(credenciales.toDto())) {
            is Resource.Error -> Resource.Error(remoteResponse.message ?: "", Unit)
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> {
                remoteResponse.data?.let {
                    local.save(it.toEntity(credenciales.rememberUser))
                    Resource.Success(Unit)
                } ?: Resource.Error(remoteResponse.message ?: "Error al modificar las credenciales")
            }
        }
    }

    override suspend fun getUsuarioLoggeado(): Usuario? =
        local.getUser()?.toDomain()
}