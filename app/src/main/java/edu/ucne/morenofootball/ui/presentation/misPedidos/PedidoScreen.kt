@file:OptIn(ExperimentalAnimationApi::class)

package edu.ucne.morenofootball.ui.presentation.misPedidos

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.EventNote
import androidx.compose.material.icons.twotone.CheckCircle
import androidx.compose.material.icons.twotone.ChevronRight
import androidx.compose.material.icons.twotone.Done
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.LocalShipping
import androidx.compose.material.icons.twotone.Schedule
import androidx.compose.material.icons.twotone.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.morenofootball.domain.pedidos.models.response.PedidoDetalleResponse
import edu.ucne.morenofootball.domain.pedidos.models.response.PedidoResponse
import edu.ucne.morenofootball.domain.pedidos.useCases.formatearFecha

@Composable
fun PedidoScreen(
    viewModel: PedidoViewModel = hiltViewModel(),
    navigateToDetalle: (Int) -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(PedidoUiEvent.LoadPedidos)
    }

    PedidoListBody(
        state = state.value,
        onEvent = viewModel::onEvent,
        navigateToDetalle = navigateToDetalle
    )
}

@Composable
fun PedidoListBody(
    state: PedidoUiState,
    onEvent: (PedidoUiEvent) -> Unit,
    navigateToDetalle: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        // Titulo
        Text(
            text = "Mis Pedidos",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Filtro por estados del pedido
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.categories) { category ->
                Card(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .animateContentSize()
                        .clickable {
                            onEvent(PedidoUiEvent.OnCategorySelected(category))
                            when (category.id) {
                                1 -> onEvent(PedidoUiEvent.LoadPedidos)
                                2 -> onEvent(PedidoUiEvent.LoadPedidosByEntrega)
                                3 -> onEvent(PedidoUiEvent.LoadPedidosByEnvio)
                            }
                        },
                    shape = MaterialTheme.shapes.small,
                    colors = CardDefaults.cardColors(
                        containerColor = if (category.isSelected)
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.surfaceVariant
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = if (category.isSelected) 4.dp else 1.dp
                    ),
                    border = if (category.isSelected)
                        BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                    else
                        null
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AnimatedVisibility(
                            visible = category.isSelected,
                            enter = fadeIn() + expandHorizontally(),
                            exit = fadeOut() + shrinkHorizontally()
                        ) {
                            Icon(
                                imageVector = Icons.TwoTone.Done,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                        Text(
                            text = category.name,
                            color = if (category.isSelected)
                                MaterialTheme.colorScheme.onPrimaryContainer
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = if (category.isSelected) FontWeight.Medium else FontWeight.Normal
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.pedidos.isEmpty() -> {
                EmptyPedidos()
            }

            else -> {
                // Lista de pedidos
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.pedidos) { pedido ->
                        PedidoCard(
                            pedido = pedido,
                            onClick = { navigateToDetalle(pedido.pedidoId) }
                        )
                    }

                    if (state.isLoading) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PedidoCard(
    pedido: PedidoResponse,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Pedido #${pedido.pedidoId}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                // Estado del pedido
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = when {
                            pedido.estaEntregado -> Icons.TwoTone.CheckCircle
                            pedido.estaEnviado -> Icons.TwoTone.LocalShipping
                            else -> Icons.TwoTone.Schedule
                        },
                        contentDescription = null,
                        tint = when {
                            pedido.estaEntregado -> MaterialTheme.colorScheme.primary
                            pedido.estaEnviado -> MaterialTheme.colorScheme.secondary
                            else -> MaterialTheme.colorScheme.outline
                        },
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = when {
                            pedido.estaEntregado -> "Entregado"
                            pedido.estaEnviado -> "Enviado"
                            else -> "Pendiente"
                        },
                        style = MaterialTheme.typography.labelMedium,
                        color = when {
                            pedido.estaEntregado -> MaterialTheme.colorScheme.primary
                            pedido.estaEnviado -> MaterialTheme.colorScheme.secondary
                            else -> MaterialTheme.colorScheme.outline
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Fechas
            Column {
                // Fecha pedido
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.TwoTone.EventNote,
                        contentDescription = "Fecha pedido",
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Fecha pedido: ${formatearFecha(pedido.fechaPedido)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (pedido.fechaEnviado.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.TwoTone.LocalShipping,
                            contentDescription = "Fecha envío",
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Fecha de envío: ${formatearFecha(pedido.fechaEnviado)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                if (pedido.fechaEntrega.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.TwoTone.Home,
                            contentDescription = "Fecha entrega",
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Entrega estimada: ${formatearFecha(pedido.fechaEntrega)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Resumen de productos
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.TwoTone.ShoppingBag,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${pedido.detalles.size} producto${if (pedido.detalles.size != 1) "s" else ""}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.weight(1f))

                // Total de items
                Text(
                    text = "${pedido.detalles.sumOf { it.cantidad }} items",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.TwoTone.ChevronRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Composable
fun EmptyPedidos() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.TwoTone.ShoppingBag,
            contentDescription = "Sin pedidos",
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "No tienes pedidos",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Cuando realices un pedido,\naparecerá aquí",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PedidosListBodyPreview() {
    PedidoListBody(
        state = PedidoUiState(
            pedidos = listOf(
                PedidoResponse(
                    pedidoId = 1,
                    usuarioId = 1,
                    tarjetaId = 1,
                    fechaPedido = "2023-05-01",
                    fechaEnviado = "2023-05-05",
                    fechaEntrega = "2023-05-10",
                    estaEntregado = true,
                    estaEnviado = true,
                    detalles = listOf(
                        PedidoDetalleResponse(
                            detalleId = 1,
                            pedidoId = 1,
                            productoId = 1,
                            cantidad = 2
                        ),
                        PedidoDetalleResponse(
                            detalleId = 2,
                            pedidoId = 1,
                            productoId = 2,
                            cantidad = 3
                        ),
                    )
                )
            ),
        ),
        onEvent = {},
        navigateToDetalle = {}
    )
}