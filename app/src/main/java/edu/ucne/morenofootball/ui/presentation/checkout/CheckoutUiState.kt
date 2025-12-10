package edu.ucne.morenofootball.ui.presentation.checkout

import edu.ucne.morenofootball.domain.carritos.models.response.CarritoResponse
import edu.ucne.morenofootball.domain.productos.models.Producto
import edu.ucne.morenofootball.domain.tarjetas.models.Tarjeta

data class CheckoutUiState(
    val carrito: CarritoResponse? = null,
    val productos: List<Producto> = emptyList(),
    val productoIndividual: Producto? = null,
    val tarjetas: List<SelectedTarjetaUiState> = emptyList(),
    val selectedTarjeta: SelectedTarjetaUiState? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class SelectedTarjetaUiState(
    val id: Int? = null,
    val tarjeta: Tarjeta? = null,
    val isSelected: Boolean = false
)
