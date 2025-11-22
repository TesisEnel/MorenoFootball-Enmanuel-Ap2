package edu.ucne.morenofootball.data.usuarios.remote

import edu.ucne.morenofootball.data.usuarios.remote.dto.request.LoginDto
import edu.ucne.morenofootball.data.usuarios.remote.dto.request.ModificarCredencialesDto
import edu.ucne.morenofootball.data.usuarios.remote.dto.request.RegisterDto
import edu.ucne.morenofootball.data.usuarios.remote.dto.response.UsuarioResponseDto
import edu.ucne.morenofootball.utils.Resource
import okio.IOException
import javax.inject.Inject

class UsuarioRemoteDataSource @Inject constructor(
    private val api: UsuarioApiService
){
    companion object {
        const val NETWORK_ERROR = "Error de conexión"
    }

    suspend fun register(credencialesDto: RegisterDto): Resource<UsuarioResponseDto> =
        try {
            val response = api.register(credencialesDto)
            if (response.isSuccessful)
                response.body()?.let { Resource.Success(it) }
                    ?: Resource.Error("Hubo un error al registrarse")
            else Resource.Error(response.message())
        } catch (e: IOException) {
            Resource.Error(NETWORK_ERROR)
        }

    suspend fun login(credencialesDto: LoginDto): Resource<UsuarioResponseDto> =
        try {
            val response = api.login(credencialesDto)
            if (response.isSuccessful)
                response.body()?.let { Resource.Success(it) }
                    ?: Resource.Error("Hubo un error al iniciar sesión")
            else Resource.Error(response.message())
        } catch (e: IOException) {
            Resource.Error(NETWORK_ERROR)
        }


    suspend fun modificarCredenciales(credencialesDto: ModificarCredencialesDto): Resource<UsuarioResponseDto> =
        try {
            val response = api.modificarCredenciales(credencialesDto)
            if (response.isSuccessful)
                response.body()?.let { Resource.Success(it) }
                    ?: Resource.Error("Hubo un error al modificar las credenciales")
            else Resource.Error(response.message())
        } catch (e: IOException) {
            Resource.Error(NETWORK_ERROR)
        }
}