package edu.ucne.morenofootball.ui.presentation.misPedidos

import edu.ucne.morenofootball.domain.pedidos.models.response.PedidoResponse
import edu.ucne.morenofootball.ui.presentation.home.SelectableCategoryUiState

data class PedidoUiState(
    val pedidos: List<PedidoResponse> = emptyList(),
    val categories: List<SelectableCategoryUiState> = getDefaultCategories(),
    val pedido: PedidoResponse? = null,
    val isLoading: Boolean = false,
    val isRefresing: Boolean = false,
    val errorMessage: String? = null
)

private fun getDefaultCategories(): List<SelectableCategoryUiState> {
    return listOf(
        SelectableCategoryUiState(1, "Todos", true),
        SelectableCategoryUiState(2, "Entregados"),
        SelectableCategoryUiState(3, "Enviados"),
    )
}