package edu.ucne.morenofootball.data.usuarios.remote.dto.response

import com.squareup.moshi.Json

data class UsuarioResponseDto(
    val usuarioId: Int,
    val username: String,
    val email: String,
    val password: String,
    @Json(name = "fechaRegistro")
    val registerDate: String
)