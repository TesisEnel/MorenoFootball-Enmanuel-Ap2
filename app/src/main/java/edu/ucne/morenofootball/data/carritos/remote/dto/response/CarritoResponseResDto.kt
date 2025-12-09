package edu.ucne.morenofootball.data.carritos.remote.dto.response

data class CarritoResponseResDto(
    val carritoId: Int,
    val usuarioId: Int?,
    val sesionAnonimaId: String? = null,
    val detalles: List<CarritoDetalleResponseResDto>
)
