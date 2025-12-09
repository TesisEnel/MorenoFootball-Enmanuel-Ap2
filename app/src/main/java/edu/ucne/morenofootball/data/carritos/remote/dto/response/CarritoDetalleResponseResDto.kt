package edu.ucne.morenofootball.data.carritos.remote.dto.response

data class CarritoDetalleResponseResDto(
    val detalleId: Int,
    val carritoId: Int,
    val productoId: Int,
    val precio: Double,
    val cantidad: Int,
    val estaSeleccionado: Boolean,
)
