package edu.ucne.morenofootball.domain.usuarios.models

data class Login(
    val email: String,
    val clave: String,
    val rememberUser: Boolean
)
