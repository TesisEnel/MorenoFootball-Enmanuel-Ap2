package edu.ucne.morenofootball.ui.presentation.carrito

import edu.ucne.morenofootball.domain.carritos.models.response.CarritoResponse
import edu.ucne.morenofootball.domain.productos.models.Producto

data class CarritoUiState(
    val productos: List<Producto> = emptyList(),
    val carrito: CarritoResponse = CarritoResponse(),
    val error: String? = null,
    val isLoading: Boolean = false
)
