@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.morenofootball.ui.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material.icons.twotone.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import edu.ucne.morenofootball.R
import edu.ucne.morenofootball.domain.productos.models.Producto
import edu.ucne.morenofootball.ui.theme.successColorDark
import edu.ucne.morenofootball.ui.theme.successColorLight

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit = {},
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigateToLogin.collect {
            navigateToLogin()
        }
    }

    HomeBody(
        state = state.value,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBody(
    state: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = {
            onEvent(HomeUiEvent.PullToRefresh)
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    focusManager.clearFocus()
                },
            contentPadding = PaddingValues(8.dp)
        ) {
            // Barra de busqueda
            item {
                SearchBar(
                    state = state,
                    onEvent = onEvent
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.header_section),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
            }

            // Categorías
            item {
                CategoriesSection(
                    state = state,
                    onEvent = onEvent
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                Text(
                    text = "Productos filtrados",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            item {// Productos filtrados
                this@LazyColumn.ProductsFilteredSection(
                    state = state,
                    onEvent = onEvent
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                Text(
                    text = "Todos los Productos",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            item { // TODOS los productos
                this@LazyColumn.AllProductsSection(
                    state = state,
                    onEvent = onEvent
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    state: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
) {
    TextField(
        value = state.searchQuery,
        onValueChange = { onEvent(HomeUiEvent.OnSearchQueryChange(it)) },
        placeholder = { Text("Buscar productos...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar"
            )
        },
        colors = TextFieldDefaults.colors(),
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
fun CategoriesSection(
    state: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(state.categories) { category ->
            CategoryChip(
                category = category,
                onEvent = onEvent
            )
        }
    }
}

@Composable
fun CategoryChip(
    category: SelectableCategoryUiState,
    onEvent: (HomeUiEvent) -> Unit,
) {
    FilterChip(
        onClick = {
            onEvent(HomeUiEvent.OnCategorySelected(category))
            onEvent(HomeUiEvent.LoadProductsByTipo(category.id))
        },
        label = {
            Text(
                text = category.name,
                color = if (category.isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Medium
            )
        },
        selected = category.isSelected,
        leadingIcon = if (category.isSelected) {
            {
                Icon(
                    imageVector = Icons.TwoTone.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else
            null,
    )
}

@Composable
fun LazyListScope.ProductsFilteredSection(
    state: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
) {
    when {
        state.isLoadingProductsFiltered -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 4.dp,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }

        state.productsFiltered.isEmpty() -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.SearchOff,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = state.message ?: "No hay productos disponibles...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }

        else -> {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.productsFiltered) { product ->
                    ProductCard(product = product)
                }
            }
        }
    }
}

@Composable
fun LazyListScope.AllProductsSection(
    state: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
) {
    when {
        state.isLoadingAllProducts -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 4.dp,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }

        state.products.isEmpty() -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.SearchOff,
                    contentDescription = "Sin productos",
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = state.message ?: "No hay productos disponibles...",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

        else -> {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                var currentRow = mutableListOf<Producto>()

                state.products.forEachIndexed { index, product ->
                    currentRow.add(product)

                    if (currentRow.size == 2 || index == state.products.lastIndex) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            currentRow.forEach { prod ->
                                Box(modifier = Modifier.weight(1f)) {
                                    ProductCard(product = prod)
                                }
                            }

                            repeat(2 - currentRow.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        currentRow.clear()
                    }
                }
            }
        }
    }
}

@Composable
fun ProductCard(product: Producto) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(180.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = product.imagenUrl,
                    contentDescription = product.nombre,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.nombre,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "RD$${"%.2f".format(product.precio)}",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = if (isSystemInDarkTheme()) successColorDark else successColorLight
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeBody(
        state = HomeUiState(
            products = listOf(
                Producto(
                    nombre = "Camiseta Oficial",
                    precio = 1500.0,
                ),
                Producto(
                    nombre = "Shorts Oficiales",
                    precio = 700.0,
                ),
                Producto(
                    nombre = "Medias",
                    precio = 250.0,
                ),
                Producto(
                    nombre = "Zapatillas",
                    precio = 3500.0,
                ),
            )
        ),
        onEvent = {}
    )
}

