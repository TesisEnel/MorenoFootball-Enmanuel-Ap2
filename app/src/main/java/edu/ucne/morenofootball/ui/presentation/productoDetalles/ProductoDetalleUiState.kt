package edu.ucne.morenofootball.ui.presentation.productoDetalles

import edu.ucne.morenofootball.domain.productos.models.Producto

data class ProductoDetalleUiState(
    val producto: Producto? = null,
    val toggleCheckOutModal: Boolean = false,
    val isFavorite: Boolean = false,
    val subTotal: Double = 0.0,
    val precioEnvio: Double = 0.0,
    val itbis: Double = 0.0,
    val total: Double = 0.0,
)