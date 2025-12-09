package edu.ucne.morenofootball.ui.presentation.productoDetalles

import edu.ucne.morenofootball.domain.productos.models.Producto

interface ProductoDetalleUiEvent {
    data class LoadProduct(val productId: Int) : ProductoDetalleUiEvent
    data class LoadProductsRelacionados(val tipoProductoId: Int) : ProductoDetalleUiEvent
    data class AgregarAlCarrito(val producto: Producto) : ProductoDetalleUiEvent
    data object ToggleNotificacion : ProductoDetalleUiEvent
    data object ToggleCheckOutModal : ProductoDetalleUiEvent
    data object OnFavoriteClick : ProductoDetalleUiEvent
}