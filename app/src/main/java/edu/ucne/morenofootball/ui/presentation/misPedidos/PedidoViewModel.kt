package edu.ucne.morenofootball.ui.presentation.misPedidos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.morenofootball.domain.pedidos.useCases.PedidoUseCases
import edu.ucne.morenofootball.domain.usuarios.useCases.UsuarioUseCases
import edu.ucne.morenofootball.ui.presentation.home.SelectableCategoryUiState
import edu.ucne.morenofootball.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PedidoViewModel @Inject constructor(
    private val pedidoUseCases: PedidoUseCases,
    private val usuarioUseCases: UsuarioUseCases,
) : ViewModel() {
    private val _state = MutableStateFlow(PedidoUiState())
    val state = _state.asStateFlow()

    fun onEvent(event: PedidoUiEvent){
        when(event){
            is PedidoUiEvent.LoadPedidos -> loadPedidos()
            is PedidoUiEvent.LoadPedido -> loadPedido(event.pedidoId)
            is PedidoUiEvent.LoadPedidosByEnvio -> loadPedidosByEnvio()
            is PedidoUiEvent.LoadPedidosByEntrega -> loadPedidosByEntrega()
            is PedidoUiEvent.OnCategorySelected -> onCategoriaSelected(event.categorySelected)
            is PedidoUiEvent.PullToRefresh -> {
                _state.value = _state.value.copy(isRefresing = !state.value.isRefresing)
                loadPedidos()
                loadPedidosByEnvio()
                loadPedidosByEntrega()
            }
        }
    }

    private fun loadPedido(pedidoId: Int) {
        viewModelScope.launch {
            val result = pedidoUseCases.getPedidoByIdUseCase(pedidoId)
            when (result){
                is Resource.Error -> _state.update {
                    it.copy(
                        isLoading = false,
                        isRefresing = false,
                        errorMessage = result.message
                    )
                }
                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                is Resource.Success -> _state.update {
                    it.copy(
                        pedido = result.data,
                        isLoading = false,
                        isRefresing = false,
                        errorMessage = null
                    )
                }
            }
        }
    }

    private fun onCategoriaSelected(categorySelected: SelectableCategoryUiState) {
        val updatedCategories = _state.value.categories.map { categoriaDentroDeListaCategorias ->
            categoriaDentroDeListaCategorias.copy(isSelected = categoriaDentroDeListaCategorias.id == categorySelected.id)
        }
        _state.update { it.copy(categories = updatedCategories) }
    }

    private fun loadPedidos() {
        viewModelScope.launch {
            val usuarioId = usuarioUseCases.getUsuarioLoggeadoUseCase()?.usuarioId ?: return@launch

            pedidoUseCases.listByUsuarioIdUseCase(usuarioId).collectLatest { result ->
                when (result) {
                    is Resource.Success -> _state.update {
                        it.copy(
                            pedidos = result.data ?: emptyList(),
                            isLoading = false,
                            isRefresing = false,
                            errorMessage = null
                        )
                    }
                    is Resource.Error -> _state.update {
                        it.copy(
                            isLoading = false,
                            isRefresing = false,
                            errorMessage = result.message
                        )
                    }
                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun loadPedidosByEnvio() {
        viewModelScope.launch {
            val usuarioId = usuarioUseCases.getUsuarioLoggeadoUseCase()?.usuarioId ?: return@launch

            pedidoUseCases.listByEnviadoUseCase(usuarioId).collectLatest { result ->
                when (result) {
                    is Resource.Success -> _state.update {
                        it.copy(
                            pedidos = result.data ?: emptyList(),
                            isLoading = false,
                            isRefresing = false,
                            errorMessage = null
                        )
                    }
                    is Resource.Error -> _state.update {
                        it.copy(
                            isLoading = false,
                            isRefresing = false,
                            errorMessage = result.message
                        )
                    }
                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun loadPedidosByEntrega() {
        viewModelScope.launch {
            val usuarioId = usuarioUseCases.getUsuarioLoggeadoUseCase()?.usuarioId ?: return@launch

            pedidoUseCases.listByEntregaUseCase(usuarioId).collectLatest { result ->
                when (result) {
                    is Resource.Success -> _state.update {
                        it.copy(
                            pedidos = result.data ?: emptyList(),
                            isLoading = false,
                            isRefresing = false,
                            errorMessage = null
                        )
                    }
                    is Resource.Error -> _state.update {
                        it.copy(
                            isLoading = false,
                            isRefresing = false,
                            errorMessage = result.message
                        )
                    }
                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }
}