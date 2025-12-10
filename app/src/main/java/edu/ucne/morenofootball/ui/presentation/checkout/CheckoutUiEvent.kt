package edu.ucne.morenofootball.ui.presentation.checkout

interface CheckoutUiEvent {
    data object LoadCarrito : CheckoutUiEvent
    data class LoadProductos(val productosIds: List<Int>) : CheckoutUiEvent
    data object LoadTarjetas : CheckoutUiEvent
    data object RealizarCompra : CheckoutUiEvent
    data class ComprarProductoIndividual(val productoId: Int?) : CheckoutUiEvent
    data class OnTarjetaSelected(val tarjeta: SelectedTarjetaUiState) : CheckoutUiEvent
}