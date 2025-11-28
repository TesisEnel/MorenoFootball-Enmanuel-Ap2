package edu.ucne.morenofootball.domain.usuarios.models

data class ModificarCredenciales(
    val username: String,
    val email: String,
    val password: String,
    val rememberUser: Boolean,
)