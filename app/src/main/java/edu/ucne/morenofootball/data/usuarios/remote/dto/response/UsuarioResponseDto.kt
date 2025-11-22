package edu.ucne.morenofootball.data.usuarios.remote.dto.response

data class UsuarioResponseDto(
    val usuarioId: Int,
    val username: String,
    val email: String,
    val password: String,
    val fechaRegistro: String
)