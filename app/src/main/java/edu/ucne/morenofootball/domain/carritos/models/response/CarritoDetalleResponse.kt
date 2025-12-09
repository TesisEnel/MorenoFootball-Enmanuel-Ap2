package edu.ucne.morenofootball.domain.carritos.models.response

data class CarritoDetalleResponse(
    val detalleId: Int = 0,
    val carritoId: Int = 0,
    val productoId: Int = 0,
    val precio: Double = 0.0,
    val cantidad: Int = 0,
    val estaSeleccionado: Boolean,
)
