package edu.ucne.morenofootball.ui.presentation.productoDetalles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AddShoppingCart
import androidx.compose.material.icons.twotone.DoneOutline
import androidx.compose.material.icons.twotone.Replay
import androidx.compose.material.icons.twotone.SearchOff
import androidx.compose.material.icons.twotone.ShoppingBag
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import edu.ucne.morenofootball.R
import edu.ucne.morenofootball.domain.productos.models.Producto
import edu.ucne.morenofootball.ui.presentation.home.ProductCard
import kotlinx.coroutines.delay

@Composable
fun ProductoDetalleScreen(
    viewModel: ProductoDetalleViewModel = hiltViewModel(),
    productoId: Int,
    onProductClick: (Int) -> Unit,
    onCheckoutProductClick: (Int) -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(productoId) {
        viewModel.onEvent(ProductoDetalleUiEvent.LoadProduct(productoId))
    }

    ProductoDetalleBody(
        state = state.value,
        onEvent = viewModel::onEvent,
        onProductClick = onProductClick,
        onCheckoutProductClick = onCheckoutProductClick
    )
}

@Composable
fun ProductoDetalleBody(
    state: ProductoDetalleUiState,
    onEvent: (ProductoDetalleUiEvent) -> Unit,
    onProductClick: (Int) -> Unit,
    onCheckoutProductClick: (Int) -> Unit
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            state.isLoading -> {
                // Muestra un indicador de carga
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            !state.errorMessage.isNullOrBlank() -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Error al cargar el producto",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 18.sp
                    )
                    Text(
                        text = state.errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { /* onEvent(ProductoDetalleUiEvent.Retry) */ }
                    ) {
                        Icon(
                            imageVector = Icons.TwoTone.Replay,
                            contentDescription = null
                        )
                        Text("Reintentar")
                    }
                }
            }

            state.producto != null -> {
                // Muestra los detalles del producto
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // Imagen del producto
                    AsyncImage(
                        model = state.producto.imagenUrl,
                        contentDescription = "Imagen de ${state.producto.nombre}",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        error = painterResource(id = R.drawable.no_disponible128px)
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    // Nombre del producto
                    Text(
                        text = state.producto.nombre,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Precio
                    Text(
                        text = "$${"%.2f".format(state.producto.precio)}",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Stock
                    Text(
                        text = if (state.producto.stock > 0) {
                            "Stock disponible: ${state.producto.stock} unidades"
                        } else {
                            "Producto agotado"
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (state.producto.stock > 0) {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            MaterialTheme.colorScheme.error
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    NotificacionAgregadoAlCarrito(
                        state = state,
                        onEvent = onEvent
                    )
                    // Boton de agregar al carrito
                    Button(
                        onClick = {
                            onEvent(ProductoDetalleUiEvent.AgregarAlCarrito(state.producto))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = state.producto.stock > 0 && !state.isAddingToCart,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        if (state.isAddingToCart) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(4.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                            )
                        } else {
                            Icon(
                                imageVector = Icons.TwoTone.AddShoppingCart,
                                contentDescription = "Agregar al carrito"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (state.producto.stock > 0) {
                                    "Agregar al carrito"
                                } else {
                                    "Sin stock"
                                },
                                fontSize = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Boton de comprar
                    Button(
                        onClick = { onCheckoutProductClick(state.producto.productoId) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = state.producto.stock > 0 && !state.isAddingToCart,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        if (state.isAddingToCart) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(4.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                            )
                        } else {
                            Icon(
                                imageVector = Icons.TwoTone.ShoppingBag,
                                contentDescription = "Comprar ahora"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (state.producto.stock > 0) {
                                    "Comprar ahora"
                                } else {
                                    "No disponible"
                                },
                                fontSize = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Descripción:",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = state.producto.descripcion,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Text(
                        text = "Productos Relacionados",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
                    )

                    ProductosRow(
                        state = state,
                        onProductClick = onProductClick
                    )
                }
            }

            else -> {
                // No hay producto para mostrar
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.TwoTone.SearchOff,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Producto no encontrado...",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { /* onEvent(ProductoDetalleUiEvent.Volver) */ }
                    ) {
                        Text("Volver")
                    }
                }
            }
        }
    }
}

@Composable
fun ProductosRow(
    state: ProductoDetalleUiState,
    onProductClick: (Int) -> Unit,
) {
    when {
        state.isLoading -> {
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

        state.productosRelacionados.isEmpty() -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.TwoTone.SearchOff,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = state.errorMessage ?: "No hay productos disponibles...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }

        else -> {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.productosRelacionados) { product ->
                    ProductCard(product = product, onProductClick)
                }
            }
        }
    }
}

@Composable
fun NotificacionAgregadoAlCarrito(
    state: ProductoDetalleUiState,
    onEvent: (ProductoDetalleUiEvent) -> Unit,
) {
    LaunchedEffect(state.toggleNotificacion) {
        if (state.toggleNotificacion) {
            delay(2000)
            onEvent(ProductoDetalleUiEvent.ToggleNotificacion)
        }
    }

    if (state.toggleNotificacion) {
        AlertDialog(
            onDismissRequest = { onEvent(ProductoDetalleUiEvent.ToggleNotificacion) },
            title = {
                Text(
                    text = "¡Éxito!",
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = state.notificacionMessage ?: "",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            },
            icon = {
                Icon(
                    Icons.TwoTone.DoneOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { onEvent(ProductoDetalleUiEvent.ToggleNotificacion) }
                ) {
                    Text(
                        text = "Aceptar",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            },
            shape = MaterialTheme.shapes.large
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductoDetalleBodyPreview() {
    ProductoDetalleBody(
        state = ProductoDetalleUiState(
            producto = Producto(
                nombre = "Camiseta Oficial",
                precio = 1500.0,
                stock = 10,
                descripcion = "Camiseta oficial del equipo",
            ),
            productosRelacionados = listOf(
                Producto(
                    nombre = "Camiseta Oficial 2",
                    precio = 1200.0,
                    stock = 10,
                    descripcion = "Camiseta oficial del equipo"
                ),
                Producto(
                    nombre = "Camiseta Oficial 3",
                    precio = 750.0,
                    stock = 10,
                    descripcion = "Camiseta oficial del equipo"
                ),
                Producto(
                    nombre = "Camiseta Oficial 4",
                    precio = 500.0,
                    stock = 10,
                    descripcion = "Camiseta oficial del equipo"
                )
            )
        ),
        onEvent = {},
        onProductClick = {},
        onCheckoutProductClick = {}
    )
}