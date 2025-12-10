package edu.ucne.morenofootball.ui.presentation.checkout

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.morenofootball.domain.carritos.models.request.UserOrSessionIdParams
import edu.ucne.morenofootball.domain.carritos.useCases.CarritoUseCases
import edu.ucne.morenofootball.domain.pedidos.models.request.PedidoDetalleRequest
import edu.ucne.morenofootball.domain.pedidos.models.request.PedidoRequest
import edu.ucne.morenofootball.domain.pedidos.useCases.PedidoUseCases
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
class CheckoutViewModel @Inject constructor(
    private val carritoUseCases: CarritoUseCases,
    private val usuarioUseCases: UsuarioUseCases,
    private val productosUseCases: ProductoUseCases,
    private val tarjetasUseCases: TarjetaUseCases,
    private val pedidosUseCases: PedidoUseCases,
) : ViewModel() {
    private val _state = MutableStateFlow(CheckoutUiState())
    val state = _state.asStateFlow()

    private val _navigateToAgradecimiento = MutableSharedFlow<Unit>()
    val navigateToAgradecimiento = _navigateToAgradecimiento.asSharedFlow()

    init {
        loadTarjetas()
    }


    fun onEvent(event: CheckoutUiEvent) {
        when (event) {
            CheckoutUiEvent.LoadCarrito -> loadCarrito()
            is CheckoutUiEvent.LoadProductos -> loadProductos(event.productosIds)
            CheckoutUiEvent.LoadTarjetas -> loadTarjetas()
            CheckoutUiEvent.RealizarCompra -> realizarCompra()
            is CheckoutUiEvent.ComprarProductoIndividual -> comprarProductoIndividual(event.productoId)
            is CheckoutUiEvent.OnTarjetaSelected -> onTarjetaSelected(event.tarjeta)
        }
    }

    private fun onTarjetaSelected(tarjetaSeleccionada: SelectedTarjetaUiState) {
        _state.update { it ->
            it.copy(
                tarjetas = it.tarjetas.map { tarjeta ->
                    tarjeta.copy(isSelected = tarjeta.tarjeta?.tarjetaId == tarjetaSeleccionada.tarjeta?.tarjetaId)
                },
                selectedTarjeta = tarjetaSeleccionada
            )
        }
    }

    private fun getProductoById(productoId: Int?) {
        viewModelScope.launch {
            val result = productosUseCases.getById(productoId ?: 0)
            when (result) {
                is Resource.Error -> _state.update {
                    it.copy(
                        error = result.message,
                        isLoading = false
                    )
                }

                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                is Resource.Success -> _state.update {
                    it.copy(
                        error = "",
                        isLoading = false,
                        productoIndividual = result.data
                    )
                }
            }
        }
    }

    private fun comprarProductoIndividual(productoId: Int?) {
        viewModelScope.launch {
            productoId?.let { productoId ->
                getProductoById(productoId)
                val usuarioId = getUsuarioId() ?: 0
                val tarjetaId = _state.value.selectedTarjeta?.tarjeta?.tarjetaId ?: 0

                val detalles = listOf(
                    PedidoDetalleRequest(
                        productoId = productoId,
                        cantidad = 1
                    )
                )
                val pedido = PedidoRequest(
                    usuarioId = usuarioId,
                    tarjetaId = tarjetaId,
                    detalles = detalles
                )
                val result = pedidosUseCases.createPedidoUseCase(pedido)
                when (result) {
                    is Resource.Error -> _state.update {
                        it.copy(
                            error = result.message,
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                    is Resource.Success -> _state.update {
                        _navigateToAgradecimiento.emit(Unit)
                        it.copy(
                            error = "",
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun realizarCompra() {
        viewModelScope.launch {
            val usuarioId = getUsuarioId() ?: 0
            val tarjetaId = _state.value.selectedTarjeta?.tarjeta?.tarjetaId ?: 0


            val detalles = _state.value.carrito?.detalles?.map {
                PedidoDetalleRequest(
                    productoId = it.productoId,
                    cantidad = it.cantidad
                )
            } ?: emptyList()

            val pedido = PedidoRequest(
                usuarioId = usuarioId,
                tarjetaId = tarjetaId,
                detalles = detalles
            )
            val result = pedidosUseCases.createPedidoUseCase(pedido)
            when (result) {
                is Resource.Error -> _state.update {
                    it.copy(
                        error = result.message,
                        isLoading = false
                    )
                }

                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                is Resource.Success -> _state.update {
                    _navigateToAgradecimiento.emit(Unit)
                    it.copy(
                        error = "",
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun loadTarjetas() {
        viewModelScope.launch {
            tarjetasUseCases.listTarjetasByUsuarioIdUsecase(getUsuarioId() ?: 0)
                .collectLatest { result ->
                    when (result) {
                        is Resource.Error -> _state.update {
                            Log.d("CheckoutViewModel", "Error: ${result.message}")
                            it.copy(
                                error = result.message,
                                isLoading = false
                            )
                        }

                        is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                        is Resource.Success -> _state.update {
                            Log.d("CheckoutViewModel", "Success: ${result.data}")
                            it.copy(
                                error = result.message,
                                isLoading = false,
                                tarjetas = result.data?.map { tarjeta ->
                                    SelectedTarjetaUiState(
                                        tarjeta = tarjeta,
                                        isSelected = false
                                    )
                                } ?: emptyList(),
                                productoIndividual = null
                            )
                        }
                    }
                }
        }
    }

    private fun loadProductos(produtosIds: List<Int>) {
        viewModelScope.launch {
            productosUseCases.listByIds(produtosIds).collectLatest { result ->
                when (result) {
                    is Resource.Error -> _state.update {
                        it.copy(
                            error = result.message,
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                    is Resource.Success -> _state.update {
                        it.copy(
                            error = result.message,
                            isLoading = false,
                            productos = result.data ?: emptyList(),
                            productoIndividual = null
                        )
                    }
                }
            }
        }
    }

    private fun loadCarrito() {
        viewModelScope.launch {
            val result =
                carritoUseCases.getByUsuarioIdUseCase(UserOrSessionIdParams(getUsuarioId() ?: 0))

            when (result) {
                is Resource.Error -> _state.update {
                    it.copy(
                        error = result.message,
                        isLoading = false
                    )
                }

                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                is Resource.Success -> {
                    val carrito = result.data
                    if (carrito != null) {
                        loadProductos(carrito.detalles.map { it.productoId })
                        _state.update {
                            it.copy(
                                error = "",
                                isLoading = false,
                                carrito = carrito
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun getUsuarioId() =
        usuarioUseCases.getUsuarioLoggeadoUseCase()?.usuarioId
}