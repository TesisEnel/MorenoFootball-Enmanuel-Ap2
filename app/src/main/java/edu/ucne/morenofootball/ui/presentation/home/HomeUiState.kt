package edu.ucne.morenofootball.ui.presentation.home

import edu.ucne.morenofootball.domain.productos.models.Producto

data class HomeUiState(
    val products: List<Producto> = emptyList(),
    val productsFiltered: List<Producto> = emptyList(),
    val categories: List<SelectableCategoryUiState> = getDefaultCategories(),
    val isLoadingAllProducts: Boolean = false,
    val isLoadingProductsFiltered: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val message: String? = null,
)

data class SelectableCategoryUiState(
    val id: Int,
    val name: String,
    val isSelected: Boolean = false
)

private fun getDefaultCategories(): List<SelectableCategoryUiState> {
    return listOf(
        SelectableCategoryUiState(1, "Camisetas Oficiales", true),
        SelectableCategoryUiState(2, "Shorts Oficiales"),
        SelectableCategoryUiState(3, "Medias"),
        SelectableCategoryUiState(4, "Sudadera"),
        SelectableCategoryUiState(5, "Jackets"),
        SelectableCategoryUiState(6, "Ropa de Entrenamiento"),
        SelectableCategoryUiState(7, "Ropa Casual"),
        SelectableCategoryUiState(8, "Balones Oficiales"),
        SelectableCategoryUiState(9, "Balones de Entrenamiento"),
        SelectableCategoryUiState(10, "Mochilas"),
        SelectableCategoryUiState(11, "Botellas"),
        SelectableCategoryUiState(12, "Guantes de Portero"),
        SelectableCategoryUiState(13, "Espinilleras"),
        SelectableCategoryUiState(14, "Gorras"),
        SelectableCategoryUiState(15, "Llaveros"),
        SelectableCategoryUiState(16, "Posters de Jugadores"),
        SelectableCategoryUiState(17, "Zapatillas")
    )
}
