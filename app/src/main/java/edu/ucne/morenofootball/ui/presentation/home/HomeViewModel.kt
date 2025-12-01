package edu.ucne.morenofootball.ui.presentation.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.morenofootball.domain.productos.useCases.ProductoUseCases
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
class HomeViewModel @Inject constructor(
    private val productoUseCases: ProductoUseCases,
    private val usuarioUseCases: UsuarioUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    private val _navigateToLogin = MutableSharedFlow<Unit>()
    val navigateToLogin = _navigateToLogin.asSharedFlow()

    init {
        getUserLoggeado()
        loadProductsByTipo(1)
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.OnSearchQueryChange -> _state.update { it.copy(searchQuery = event.query) }
            is HomeUiEvent.OnCategorySelected -> onCategoriaSelected(event.category)
            is HomeUiEvent.PullToRefresh -> _state.update { it.copy(isRefreshing = !it.isRefreshing) }
            is HomeUiEvent.LoadProducts -> loadProducts()
            is HomeUiEvent.LoadProductsByTipo -> loadProductsByTipo(event.tipo)
        }
    }

    private fun getUserLoggeado() {
        viewModelScope.launch {
            val user = usuarioUseCases.getUsuarioLoggeadoUseCase()
            if(user == null)
                _navigateToLogin.emit(Unit)
        }
    }

    private fun onCategoriaSelected(category: SelectableCategoryUiState) {
        val updatedCategories = _state.value.categories.map {
            it.copy(isSelected = it.id == category.id)
        }
        _state.update { it.copy(categories = updatedCategories) }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            productoUseCases.listByAvailability().collectLatest { result ->
                when (result) {
                    is Resource.Success -> _state.update {
                        it.copy(
                            products = result.data ?: emptyList(),
                            isLoading = false,
                            error = null
                        )
                    }


                    is Resource.Error -> _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message,
                            message = "No hay productos...",
                            products = emptyList()
                        )
                    }

                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun loadProductsByTipo(tipo: Int) {
        viewModelScope.launch {
            productoUseCases.listByTipo(tipo).collectLatest { result ->
                when (result) {
                    is Resource.Success -> _state.update {
                        it.copy(
                            productsFiltered = result.data ?: emptyList(),
                            isLoading = false,
                            error = null
                        )
                    }


                    is Resource.Error -> _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message,
                            message = "No hay productos...",
                            productsFiltered = emptyList()
                        )
                    }

                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }
}