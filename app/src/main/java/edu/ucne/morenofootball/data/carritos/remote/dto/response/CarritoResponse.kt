package edu.ucne.morenofootball.data.carritos.remote.dto.response

data class CarritoResponse(
    val carritoId: Int,
    val usuarioId: Int?,
    val sesionAnonimaId: String? = null,
    val detalles: CarritoDetalleResponse
)
