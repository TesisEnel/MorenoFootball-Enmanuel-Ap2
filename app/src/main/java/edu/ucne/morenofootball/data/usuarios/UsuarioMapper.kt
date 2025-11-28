package edu.ucne.morenofootball.data.usuarios

import edu.ucne.morenofootball.data.usuarios.local.UsuarioEntity
import edu.ucne.morenofootball.data.usuarios.remote.dto.request.LoginDto
import edu.ucne.morenofootball.data.usuarios.remote.dto.request.ModificarCredencialesDto
import edu.ucne.morenofootball.data.usuarios.remote.dto.request.RegisterDto
import edu.ucne.morenofootball.data.usuarios.remote.dto.response.UsuarioResponseDto
import edu.ucne.morenofootball.domain.usuarios.models.Login
import edu.ucne.morenofootball.domain.usuarios.models.ModificarCredenciales
import edu.ucne.morenofootball.domain.usuarios.models.Register
import edu.ucne.morenofootball.domain.usuarios.models.Usuario

fun Register.toDto() = RegisterDto(
    username,
    email,
    password
)

fun Login.toDto() = LoginDto(
    email,
    password
)

fun ModificarCredenciales.toDto() = ModificarCredencialesDto(
    username,
    email,
    password,
)

fun UsuarioResponseDto.toEntity(rememberUser: Boolean) = UsuarioEntity(
    usuarioId,
    username,
    email,
    password,
    registerDate,
    rememberUser
)

fun UsuarioEntity.toDomain() = Usuario(
    usuarioId,
    username,
    email,
    password,
    registerDate,
    rememberUser
)

