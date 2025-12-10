package edu.ucne.morenofootball.ui.presentation.misPedidos

import edu.ucne.morenofootball.ui.presentation.home.SelectableCategoryUiState

interface PedidoUiEvent {
    data class OnCategorySelected(val categorySelected : SelectableCategoryUiState) : PedidoUiEvent
    data object LoadPedidos : PedidoUiEvent
    data class LoadPedido(val pedidoId: Int) : PedidoUiEvent
    data object LoadPedidosByEnvio : PedidoUiEvent
    data object LoadPedidosByEntrega : PedidoUiEvent
    data object PullToRefresh : PedidoUiEvent
}