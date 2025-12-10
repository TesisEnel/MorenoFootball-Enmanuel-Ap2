package edu.ucne.morenofootball.ui.presentation.home

interface HomeUiEvent {
    data object LoadProducts : HomeUiEvent
    data class LoadProductsByTipo(val tipo: Int) : HomeUiEvent
    data class OnCategorySelected(val category: SelectableCategoryUiState) : HomeUiEvent
    data object PullToRefresh : HomeUiEvent
}