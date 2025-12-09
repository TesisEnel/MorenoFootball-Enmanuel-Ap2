package edu.ucne.morenofootball.data.carritos.remote.dto.request

data class AgregarProductoParamsReqDto(
    val usuarioId: Int?,
    val productoId: Int,
    val cantidad: Int = 1,
    val sesionAnonimaId: String? = null
)
