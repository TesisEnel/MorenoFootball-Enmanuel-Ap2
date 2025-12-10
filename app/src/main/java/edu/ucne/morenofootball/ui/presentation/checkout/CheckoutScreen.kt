package edu.ucne.morenofootball.ui.presentation.checkout

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.CheckCircle
import androidx.compose.material.icons.twotone.ChevronRight
import androidx.compose.material.icons.twotone.CreditCard
import androidx.compose.material.icons.twotone.CreditCardOff
import androidx.compose.material.icons.twotone.Image
import androidx.compose.material.icons.twotone.Numbers
import androidx.compose.material.icons.twotone.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import edu.ucne.morenofootball.domain.carritos.models.response.CarritoDetalleResponse
import edu.ucne.morenofootball.domain.productos.models.Producto
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CheckoutScreen(
    viewModel: CheckoutViewModel = hiltViewModel(),
    navigateToAgradecimiento: () -> Unit,
    productoId: Int? = null,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigateToAgradecimiento.collectLatest {
            navigateToAgradecimiento()
        }

        Log.d("CheckoutScreen", "Se entro al launched effect")
        viewModel.onEvent(CheckoutUiEvent.LoadCarrito)
    }

    state.value.carrito?.let { carrito ->
        LaunchedEffect(carrito.detalles) {
            val productoIds = carrito.detalles.map { it.productoId }
            viewModel.onEvent(CheckoutUiEvent.LoadProductos(productoIds))
        }
    }

    CheckoutBody(
        state = state.value,
        onEvent = viewModel::onEvent,
        productoId = productoId
    )
}

@Composable
fun CheckoutBody(
    state: CheckoutUiState,
    onEvent: (CheckoutUiEvent) -> Unit,
    productoId: Int? = null,
) {
    var showTarjetaBottomSheet by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        )
        {
            // Titulo
            Text(
                text = "Finalizar Compra",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )

            // Resumen del pedido
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Resumen del Pedido",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Productos
                    state.carrito?.detalles?.forEach { detalle ->
                        val producto = state.productos.find { it.productoId == detalle.productoId }
                        ProductoCheckoutItem(
                            producto = producto,
                            detalle = detalle,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Totales
                    val itbisTotal = state.carrito?.detalles?.sumOf {
                        (it.precio * it.cantidad) * 0.18
                    } ?: 0.0
                    val subtotal = state.carrito?.detalles?.sumOf { it.precio * it.cantidad } ?: 0.0
                    val total = subtotal + itbisTotal

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Subtotal", style = MaterialTheme.typography.bodyLarge)
                            Text("$ ${"%.2f".format(subtotal)}", style = MaterialTheme.typography.bodyLarge)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("ITBIS", style = MaterialTheme.typography.bodyLarge)
                            Text("$ ${"%.2f".format(itbisTotal)}", style = MaterialTheme.typography.bodyLarge)
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Total",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "$ ${"%.2f".format(total)}",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            // Metodo de pago
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .clickable { showTarjetaBottomSheet = true },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.TwoTone.CreditCard,
                        contentDescription = "Tarjeta",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Método de pago",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        if (state.selectedTarjeta != null) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "•••• ${state.selectedTarjeta.tarjeta?.bin.toString().drop(12)}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = state.selectedTarjeta.tarjeta?.tipoTarjeta ?: "",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        } else {
                            Text(
                                text = "Seleccionar tarjeta",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.TwoTone.ChevronRight,
                        contentDescription = "Seleccionar",
                        tint = MaterialTheme.colorScheme.outline
                    )
                }
            }

            // Boton de pago
            Button(
                onClick = {
                    if (productoId == null || productoId < 1)
                        onEvent(CheckoutUiEvent.RealizarCompra) // Esto hace una compra de lo que se tenga en el carrito
                    else
                        onEvent(CheckoutUiEvent.ComprarProductoIndividual(productoId)) // Esto hace la compra individual de un producto
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                enabled = state.selectedTarjeta != null && !state.isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 4.dp
                )
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Icon(
                        imageVector = Icons.TwoTone.ShoppingBag,
                        contentDescription = "Comprar",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Completar Compra",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

        // Bottom Sheet para seleccionar tarjeta
        if (showTarjetaBottomSheet) {
            Log.d("CheckoutScreen", "Producto a comprar: ${state.productoIndividual}, productoId: $productoId")

            TarjetaSelectionBottomSheet(
                tarjetas = state.tarjetas,
                onTarjetaSelected = { tarjeta ->
                    onEvent(CheckoutUiEvent.OnTarjetaSelected(tarjeta))
                    showTarjetaBottomSheet = false
                },
                onDismiss = { showTarjetaBottomSheet = false }
            )
        }

        // Loading overlay
        if (state.isLoading && state.carrito == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Error
        state.error?.let { error ->
            LaunchedEffect(error) {
                // Puedes mostrar un snackbar aquí
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarjetaSelectionBottomSheet(
    tarjetas: List<SelectedTarjetaUiState>,
    onTarjetaSelected: (SelectedTarjetaUiState) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 16.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Text(
                text = "Seleccionar Tarjeta",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            if (tarjetas.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.TwoTone.CreditCardOff,
                        contentDescription = "Sin tarjetas",
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No tienes tarjetas guardadas",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(tarjetas) { tarjeta ->
                        TarjetaItem(
                            tarjeta = tarjeta,
                            onSelected = { onTarjetaSelected(tarjeta) }
                        )
                    }
                }
            }

            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Text("Cancelar")
            }
        }
    }
}

@Composable
fun TarjetaItem(
    tarjeta: SelectedTarjetaUiState,
    onSelected: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelected),
        colors = CardDefaults.cardColors(
            containerColor = if (tarjeta.isSelected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surfaceVariant
        ),
        border = if (tarjeta.isSelected)
            BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        else
            null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                if (tarjeta.isSelected) {
                    Icon(
                        imageVector = Icons.TwoTone.CheckCircle,
                        contentDescription = "Seleccionada",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.TwoTone.CreditCard,
                        contentDescription = "Tarjeta",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "•••• •••• •••• ${tarjeta.tarjeta?.bin.toString().drop(12)}",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = tarjeta.tarjeta?.tipoTarjeta ?: "",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Vence: ${tarjeta.tarjeta?.fechaVencimiento ?: "**/**"}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ProductoCheckoutItem(
    producto: Producto?,
    detalle: CarritoDetalleResponse,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                if (producto?.imagenUrl?.isNotEmpty() == true) {
                    AsyncImage(
                        model = producto.imagenUrl,
                        contentDescription = producto.nombre,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        imageVector = Icons.TwoTone.Image,
                        contentDescription = "Sin imagen",
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.Center),
                        tint = MaterialTheme.colorScheme.outline
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Informacion del producto
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = producto?.nombre ?: "Producto #${detalle.productoId}",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "RD$ ${"%.2f".format(detalle.precio)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Icon(
                        imageVector = Icons.TwoTone.Numbers,
                        contentDescription = "Cantidad",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "x${detalle.cantidad}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "RD$ ${"%.2f".format(detalle.precio * detalle.cantidad)}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
            }

            // Indicador de seleccion
            if (detalle.estaSeleccionado) {
                Icon(
                    imageVector = Icons.TwoTone.CheckCircle,
                    contentDescription = "Seleccionado",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProductoCheckoutItemPreview() {
    ProductoCheckoutItem(
        producto = Producto(),
        detalle = CarritoDetalleResponse(estaSeleccionado = true),
    )
}

@Preview(showBackground = true)
@Composable
fun TarjetaItemPreview() {
    TarjetaItem(
        tarjeta = SelectedTarjetaUiState(),
        onSelected = {}
    )
}

@Preview(showBackground = true)
@Composable
fun TarjetaSelectionBottomSheetPreview() {
    TarjetaSelectionBottomSheet(
        tarjetas = listOf(
            SelectedTarjetaUiState(),
            SelectedTarjetaUiState(),
            SelectedTarjetaUiState(),
        ),
        onTarjetaSelected = {},
        onDismiss = {}
    )
}