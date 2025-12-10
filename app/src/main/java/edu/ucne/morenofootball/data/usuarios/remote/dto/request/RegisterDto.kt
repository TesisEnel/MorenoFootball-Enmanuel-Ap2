package edu.ucne.morenofootball.data.usuarios.remote.dto.request

data class RegisterDto(
    val username: String,
    val email: String,
    val clave: String,
    val rol: Int = 2
)