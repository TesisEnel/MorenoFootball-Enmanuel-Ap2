package edu.ucne.morenofootball.data.carritos.remote.dto.request

data class ActionWithProductFromCardParamsReqDto(
    val usuarioId: Int?,
    val productoId: Int,
    val sesionAnonimaId: String? = null
)
