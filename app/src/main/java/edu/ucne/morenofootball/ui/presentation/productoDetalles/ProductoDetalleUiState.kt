package edu.ucne.morenofootball.ui.presentation.productoDetalles

import edu.ucne.morenofootball.domain.productos.models.Producto
import edu.ucne.morenofootball.domain.tarjetas.models.Tarjeta

data class ProductoDetalleUiState(
    val producto: Producto? = null,
    val productosRelacionados: List<Producto> = emptyList(),
    val toggleCheckOutModal: Boolean = false,
    val tarjetaSeleccionada: TarjetaSeleccionada? = null,
    val listaTarjetas: List<Tarjeta> = emptyList(),
    val isFavorite: Boolean = false,
    val isLoading: Boolean = false,
    val isAddingToCart: Boolean = false,
    val errorMessage: String? = null,
    val notificacionMessage: String? = null,
    val toggleNotificacion: Boolean = false
)

data class TarjetaSeleccionada(
    val tarjeta: Tarjeta? = null,
    val isSelected: Boolean = false,
)