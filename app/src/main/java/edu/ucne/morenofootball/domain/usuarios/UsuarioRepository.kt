package edu.ucne.morenofootball.domain.usuarios

import edu.ucne.morenofootball.domain.usuarios.models.Login
import edu.ucne.morenofootball.domain.usuarios.models.ModificarCredenciales
import edu.ucne.morenofootball.domain.usuarios.models.Register
import edu.ucne.morenofootball.domain.usuarios.models.Usuario
import edu.ucne.morenofootball.utils.Resource

interface UsuarioRepository {
    suspend fun register(credenciales: Register): Resource<Unit>
    suspend fun login(credenciales: Login): Resource<Unit>
    suspend fun modificarCredenciales(credenciales: ModificarCredenciales): Resource<Unit>
    suspend fun getUsuarioLoggeado(): Usuario?
}