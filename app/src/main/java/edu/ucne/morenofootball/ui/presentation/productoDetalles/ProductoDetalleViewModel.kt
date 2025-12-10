package edu.ucne.morenofootball.ui.presentation.productoDetalles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.morenofootball.domain.carritos.models.request.AgregarProductoParams
import edu.ucne.morenofootball.domain.carritos.useCases.CarritoUseCases
import edu.ucne.morenofootball.domain.productos.models.Producto
import edu.ucne.morenofootball.domain.productos.useCases.ProductoUseCases
import edu.ucne.morenofootball.domain.tarjetas.useCases.TarjetaUseCases
import edu.ucne.morenofootball.domain.usuarios.useCases.UsuarioUseCases
import edu.ucne.morenofootball.utils.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductoDetalleViewModel @Inject constructor(
    private val productoUseCases: ProductoUseCases,
    private val carritoUseCases: CarritoUseCases,
    private val usuarioUseCases: UsuarioUseCases,
) : ViewModel() {
    private val _state = MutableStateFlow(ProductoDetalleUiState())
    val state = _state.asStateFlow()

    private val _navigateToAgradecimiento = MutableSharedFlow<Unit>()
    val navigateToAgradecimiento = _navigateToAgradecimiento.asSharedFlow()

    fun onEvent(event: ProductoDetalleUiEvent) {
        when (event) {
            is ProductoDetalleUiEvent.LoadProduct -> loadProduct(event.productId)
            is ProductoDetalleUiEvent.LoadProductsRelacionados -> loadProductsRelacionados(event.tipoProductoId)
            is ProductoDetalleUiEvent.AgregarAlCarrito -> agregarAlCarrito(event.producto)
            is ProductoDetalleUiEvent.ToggleCheckOutModal -> _state.update {
                it.copy(
                    toggleCheckOutModal = !it.toggleCheckOutModal
                )
            }

            is ProductoDetalleUiEvent.ToggleNotificacion -> _state.update { it.copy(toggleNotificacion = !it.toggleNotificacion) }
            is ProductoDetalleUiEvent.OnFavoriteClick -> onFavoriteClick()
        }
    }

    private suspend fun getUsuarioId() =
        usuarioUseCases.getUsuarioLoggeadoUseCase()?.usuarioId ?: 0

    private fun agregarAlCarrito(producto: Producto) {
        viewModelScope.launch {
            _state.update { it.copy(isAddingToCart = true) }

            val usuarioId = getUsuarioId()
            if (usuarioId == 0) {
                _state.update {
                    it.copy(
                        notificacionMessage = "Ha ocurrido un error al obtener el usuario...",
                        toggleNotificacion = true,
                        isAddingToCart = false
                    )
                }
                return@launch
            }

            when (val result = carritoUseCases.agregarProductoUseCase(
                AgregarProductoParams(
                    usuarioId,
                    producto.productoId
                )
            )) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            notificacionMessage = "¡Producto agregado al carrito!",
                            isAddingToCart = false,
                            toggleNotificacion = true
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            notificacionMessage = "Ha ocurrido un error al agregar al carrito...",
                            isAddingToCart = false,
                            toggleNotificacion = true
                        )
                    }
                }

                is Resource.Loading -> _state.update {
                    it.copy(
                        isAddingToCart = true,
                        toggleNotificacion = false
                    )
                }
            }
        }
    }

    private fun loadProductsRelacionados(tipoProductoId: Int) {
        viewModelScope.launch {
            productoUseCases.listByTipo(tipoProductoId).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                productosRelacionados = result.data ?: emptyList(),
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.message,
                                productosRelacionados = emptyList()
                            )
                        }
                    }

                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun loadProduct(prouctoId: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isAddingToCart = false,
                    toggleNotificacion = false,
                    notificacionMessage = null
                )
            }

            when (val result = productoUseCases.getById(prouctoId)) {
                is Resource.Success -> {
                    loadProductsRelacionados(result.data!!.tipoProducto)
                    _state.update {
                        it.copy(
                            producto = result.data,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message,
                            producto = null
                        )
                    }
                }

                is Resource.Loading -> _state.update { it.copy(isLoading = true) }

            }
        }
    }

    private fun onFavoriteClick() {
        viewModelScope.launch {
            // TODO()
        }
    }
}