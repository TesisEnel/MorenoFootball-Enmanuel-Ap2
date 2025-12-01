package edu.ucne.morenofootball.ui.presentation.home

interface HomeUiEvent {
    data class OnSearchQueryChange(val query: String) : HomeUiEvent
    data class OnCategorySelected(val category: SelectableCategoryUiState) : HomeUiEvent
}