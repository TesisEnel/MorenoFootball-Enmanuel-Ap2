package edu.ucne.morenofootball.data.usuarios.remote

import edu.ucne.morenofootball.data.usuarios.remote.dto.request.LoginDto
import edu.ucne.morenofootball.data.usuarios.remote.dto.request.ModificarCredencialesDto
import edu.ucne.morenofootball.data.usuarios.remote.dto.request.RegisterDto
import edu.ucne.morenofootball.data.usuarios.remote.dto.response.UsuarioResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UsuarioApiService {
    @POST("api/Usuarios/register")
    suspend fun register(@Body credenciales: RegisterDto): Response<UsuarioResponseDto>

    @POST("api/Usuarios/login")
    suspend fun login(@Body credenciales: LoginDto): Response<UsuarioResponseDto>

    @PUT("api/Usuarios")
    suspend fun modificarCredenciales(@Body credencialesDto: ModificarCredencialesDto): Response<UsuarioResponseDto>
}