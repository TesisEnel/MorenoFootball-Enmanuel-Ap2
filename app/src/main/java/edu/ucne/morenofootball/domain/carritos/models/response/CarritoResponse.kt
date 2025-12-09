package edu.ucne.morenofootball.domain.carritos.models.response

data class CarritoResponse(
    val carritoId: Int? = 0,
    val usuarioId: Int? = 0,
    val sesionAnonimaId: String? = null,
    val detalles: List<CarritoDetalleResponse> = emptyList()
)
