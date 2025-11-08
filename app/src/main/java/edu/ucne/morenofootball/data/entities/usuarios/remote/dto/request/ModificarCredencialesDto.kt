package edu.ucne.morenofootball.data.entities.usuarios.remote.dto.request

data class ModificarCredencialesDto(
    val username: String,
    val email: String,
    val password: String,
)