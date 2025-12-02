package edu.ucne.morenofootball.ui.presentation.productoDetalles

interface ProductoDetalleUiEvent {
    data class LoadProduct(val productId: Int) : ProductoDetalleUiEvent
    data object ToggleCheckOutModal : ProductoDetalleUiEvent
    data object OnFavoriteClick : ProductoDetalleUiEvent
    data object OnCartClick : ProductoDetalleUiEvent
}