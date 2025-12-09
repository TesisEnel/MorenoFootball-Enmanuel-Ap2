package edu.ucne.morenofootball.ui.presentation.carrito

interface CarritoUiEvent {
    data object LoadCarrito: CarritoUiEvent
    data class LoadProductosEnCarrito(val productosIds: List<Int>): CarritoUiEvent
    data class DeleteProduct(val carritoDetalleId: Int): CarritoUiEvent
    data class AumentarCantidad(val carritoDetalleId: Int): CarritoUiEvent
    data class DisminuirCantidad(val carritoDetalleId: Int): CarritoUiEvent
    data object VaciarCarrito: CarritoUiEvent
}