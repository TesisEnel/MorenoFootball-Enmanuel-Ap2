package edu.ucne.morenofootball.ui.presentation.productoDetalles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.morenofootball.domain.productos.useCases.ProductoUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductoDetalleViewModel @Inject constructor(
    private val productoUseCases: ProductoUseCases,
) : ViewModel() {
    private val _state = MutableStateFlow(ProductoDetalleUiState())
    val state = _state.asStateFlow()

    fun onEvent(event: ProductoDetalleUiEvent) {
        when (event) {
            is ProductoDetalleUiEvent.LoadProduct -> loadProduct(event.productId)
            is ProductoDetalleUiEvent.ToggleCheckOutModal -> _state.update { it.copy(toggleCheckOutModal = !it.toggleCheckOutModal) }
            is ProductoDetalleUiEvent.OnFavoriteClick -> onFavoriteClick()
            is ProductoDetalleUiEvent.OnCartClick -> onCartClick()
        }
    }

    private fun loadProduct(prouctoId: Int) {
        viewModelScope.launch {
            // TODO()
        }
    }

    private fun onCartClick() {
        viewModelScope.launch {
            // TODO()
        }
    }

    private fun onFavoriteClick() {
        viewModelScope.launch {
            // TODO()
        }
    }
}