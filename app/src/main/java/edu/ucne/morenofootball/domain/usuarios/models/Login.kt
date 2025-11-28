package edu.ucne.morenofootball.domain.usuarios.models

data class Login(
    val email: String,
    val password: String,
    val rememberUser: Boolean
)
