package edu.ucne.morenofootball.domain.usuarios.models

data class Usuario(
    val usuarioId: Int = 0,
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val fechaRegistro: String = "",
    val rememberUser: Boolean = false,
)