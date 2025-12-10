package edu.ucne.morenofootball.ui.presentation.carrito

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.morenofootball.domain.carritos.models.request.ActionWithProductFromCardParams
import edu.ucne.morenofootball.domain.carritos.models.request.UserOrSessionIdParams
import edu.ucne.morenofootball.domain.carritos.models.response.CarritoResponse
import edu.ucne.morenofootball.domain.carritos.useCases.CarritoUseCases
import edu.ucne.morenofootball.domain.productos.useCases.ProductoUseCases
import edu.ucne.morenofootball.domain.usuarios.useCases.UsuarioUseCases
import edu.ucne.morenofootball.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarritoViewModel @Inject constructor(
    private val carritoUseCases: CarritoUseCases,
    private val productoUseCases: ProductoUseCases,
    private val usuarioUseCases: UsuarioUseCases,
) : ViewModel() {
    private val _state = MutableStateFlow(CarritoUiState())
    val state = _state.asStateFlow()

    val itemCount = _state.map { state ->
        state.carrito.detalles.sumOf { it.cantidad }
    }

    init {
        loadCarrito()
    }

    fun onEvent(event: CarritoUiEvent) {
        when (event) {
            is CarritoUiEvent.LoadCarrito -> loadCarrito()
            is CarritoUiEvent.LoadProductosEnCarrito -> loadProductosEnCarrito(event.productosIds)
            is CarritoUiEvent.AumentarCantidad -> aumentarCantidad(event.carritoDetalleId)
            is CarritoUiEvent.DisminuirCantidad -> disminuirCantidad(event.carritoDetalleId)
            is CarritoUiEvent.DeleteProduct -> deleteProduct(event.carritoDetalleId)
            is CarritoUiEvent.VaciarCarrito -> vaciarCarrito()
        }
    }

    private fun vaciarCarrito() {
        viewModelScope.launch {
            val result = carritoUseCases.vaciarCarritoUseCase(UserOrSessionIdParams(getUsuarioId()))
            when (result) {
                is Resource.Success -> {
                    _state.update { it.copy(isLoading = false, error = null) }
                }
                is Resource.Error -> _state.update {
                    it.copy(
                        isLoading = false,
                        error = result.message ?: "Error al vaciar el carrito"
                    )
                }

                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
            }
            loadCarrito()
        }
    }

    private fun deleteProduct(productoId: Int) {
        viewModelScope.launch {
            val result = carritoUseCases.deleteProductUseCase(
                ActionWithProductFromCardParams(
                    usuarioId = getUsuarioId(),
                    productoId = productoId,
                )
            )
            when (result) {
                is Resource.Success -> {
                    _state.update { it.copy(isLoading = false, error = null) }
                    loadCarrito()
                }

                is Resource.Error -> _state.update {
                    it.copy(
                        isLoading = false,
                        error = result.message ?: "Error al eliminar el prodcuto del carrito"
                    )
                }

                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
            }
        }
    }

    private fun disminuirCantidad(productoId: Int) {
        viewModelScope.launch {
            val result = carritoUseCases.disminuirCantidadUseCase(
                ActionWithProductFromCardParams(
                    usuarioId = getUsuarioId(),
                    productoId = productoId,
                )
            )
            when (result) {
                is Resource.Success -> {
                    _state.update { it ->
                        it.copy(
                            isLoading = false,
                            error = null,
                        )
                    }
                    loadCarrito()
                }

                is Resource.Error -> _state.update {
                    it.copy(
                        isLoading = false,
                        error = result.message ?: "Error al disminuir la cantidad"
                    )
                }

                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
            }
        }
    }

    private fun aumentarCantidad(productoId: Int) {
        viewModelScope.launch {
            val result = carritoUseCases.aumentarCantidadUseCase(
                ActionWithProductFromCardParams(
                    usuarioId = getUsuarioId(),
                    productoId = productoId,
                )
            )
            when (result) {
                is Resource.Success -> {
                    _state.update { it ->
                        it.copy(
                            isLoading = false,
                            error = null,
                        )
                    }
                    loadCarrito()
                }

                is Resource.Error -> _state.update {
                    it.copy(
                        isLoading = false,
                        error = result.message ?: "Error al aumentar la cantidad",
                    )
                }

                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
            }
        }
    }

    private fun loadCarrito() {
        viewModelScope.launch {
            val result =
                carritoUseCases.getByUsuarioIdUseCase(UserOrSessionIdParams(getUsuarioId()))
            when (result) {
                is Resource.Success -> {
                    val productosIds = result.data?.detalles?.map { it.productoId } ?: emptyList()
                    loadProductosEnCarrito(productosIds)
                    Log.d("IDS de productos", "Loading ids de Productos: $productosIds")
                    _state.update {
                        it.copy(
                            carrito = result.data ?: CarritoResponse(),
                            isLoading = false,
                            error = null
                        )
                    }

                }

                is Resource.Error -> _state.update {
                    it.copy(
                        isLoading = false,
                        error = result.message ?: "Error al cargar el carrito"
                    )
                }

                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
            }
        }
    }

    private fun loadProductosEnCarrito(productosIds: List<Int>) {
        viewModelScope.launch {
            productoUseCases.listByIds(productosIds).collectLatest { result ->
                when (result) {
                    is Resource.Success -> _state.update {
                        it.copy(
                            productos = result.data ?: emptyList(),
                            isLoading = false,
                            error = null
                        )
                    }

                    is Resource.Error -> _state.update {
                        it.copy(
                            isLoading = false,
                            error = "Error al cargar los productos"
                        )
                    }

                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private suspend fun getUsuarioId(): Int? =
        usuarioUseCases.getUsuarioLoggeadoUseCase()?.usuarioId
}