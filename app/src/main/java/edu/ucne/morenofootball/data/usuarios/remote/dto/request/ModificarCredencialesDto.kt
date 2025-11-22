package edu.ucne.morenofootball.data.usuarios.remote.dto.request

data class ModificarCredencialesDto(
    val username: String,
    val email: String,
    val password: String,
)