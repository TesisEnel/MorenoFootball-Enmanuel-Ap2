package edu.ucne.morenofootball.data.carritos.remote.dto.request

data class UserOrSessionIdParamsReqDto(
    val usuarioId: Int?,
    val sesionAnonimaId: String? = null
)
