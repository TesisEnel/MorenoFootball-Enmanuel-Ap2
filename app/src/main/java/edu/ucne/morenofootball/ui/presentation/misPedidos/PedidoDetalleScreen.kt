package edu.ucne.morenofootball.ui.presentation.misPedidos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ChevronRight
import androidx.compose.material.icons.twotone.Done
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.LocalShipping
import androidx.compose.material.icons.twotone.Numbers
import androidx.compose.material.icons.twotone.ShoppingBag
import androidx.compose.material.icons.twotone.ShoppingCart
import androidx.compose.material.icons.twotone.Tag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.morenofootball.domain.pedidos.models.response.PedidoDetalleResponse
import edu.ucne.morenofootball.domain.pedidos.models.response.PedidoResponse
import edu.ucne.morenofootball.domain.pedidos.useCases.formatearFecha

@Composable
fun PedidoDetalleScreen(
    viewModel: PedidoViewModel = hiltViewModel(),
    pedidoId: Int
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(pedidoId) {
        viewModel.onEvent(PedidoUiEvent.LoadPedido(pedidoId))
    }

    PedidoDetalleBody(
        state = state.value,
    )
}

@Composable
fun PedidoDetalleBody(
    state: PedidoUiState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // estado del pedido
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Titulo del pedido
                Text(
                    text = "Pedido #${state.pedido?.pedidoId}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Timeline de estados
                TimelineEstados(pedido = state.pedido ?: return@Card)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Titulo de productos
        Text(
            text = "Productos (${state.pedido?.detalles?.size})",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Lista de productos
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(state.pedido?.detalles ?: emptyList()) { detalle ->
                ProductoDetalleCard(detalle = detalle)
            }
        }
    }
}

@Composable
fun TimelineEstados(pedido: PedidoResponse) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        EstadoTimeline(
            icon = Icons.TwoTone.ShoppingBag,
            label = "Pedido realizado",
            fecha = pedido.fechaPedido,
            isActive = true,
            isCompleted = true
        )

        // Linea conectora
        Spacer(modifier = Modifier.height(4.dp))
        LineaConectora(isCompleted = pedido.estaEnviado || pedido.estaEntregado)

        EstadoTimeline(
            icon = Icons.TwoTone.LocalShipping,
            label = "Enviado",
            fecha = pedido.fechaEnviado,
            isActive = pedido.estaEnviado,
            isCompleted = pedido.estaEnviado || pedido.estaEntregado
        )

        // Línea conectora
        Spacer(modifier = Modifier.height(4.dp))
        LineaConectora(isCompleted = pedido.estaEntregado)

        EstadoTimeline(
            icon = Icons.TwoTone.Home,
            label = "Entregado",
            fecha = pedido.fechaEntrega,
            isActive = pedido.estaEntregado,
            isCompleted = pedido.estaEntregado
        )
    }
}

@Composable
fun EstadoTimeline(
    icon: ImageVector,
    label: String,
    fecha: String,
    isActive: Boolean,
    isCompleted: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Circulo del estado
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = when {
                        isCompleted -> MaterialTheme.colorScheme.primary
                        isActive -> MaterialTheme.colorScheme.primaryContainer
                        else -> MaterialTheme.colorScheme.surfaceVariant
                    },
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isCompleted) {
                Icon(
                    imageVector = Icons.TwoTone.Done,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isActive)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Informacion del estado
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                color = if (isActive)
                    MaterialTheme.colorScheme.onSurface
                else
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )

            if (fecha.isNotEmpty()) {
                Text(
                    text = formatearFecha(fecha),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else if (isActive) {
                Text(
                    text = "En proceso...",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                Text(
                    text = "Pendiente",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

        // Indicador de estado actual
        if (isActive && !isCompleted) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
fun LineaConectora(isCompleted: Boolean) {
    Column(
        modifier = Modifier
            .width(40.dp)
            .height(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Linea vertical simple
        Box(
            modifier = Modifier
                .width(2.dp)
                .fillMaxHeight()
                .background(
                    color = if (isCompleted)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                )
        )
    }
}

@Composable
fun ProductoDetalleCard(detalle: PedidoDetalleResponse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.TwoTone.ShoppingCart,
                    contentDescription = "Producto",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Informacion del producto
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Producto #${detalle.productoId}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.TwoTone.Numbers,
                        contentDescription = "Cantidad",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Cantidad: ${detalle.cantidad}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Icon(
                        imageVector = Icons.TwoTone.Tag,
                        contentDescription = "ID",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "ID: ${detalle.detalleId}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Flecha indicadora
            Icon(
                imageVector = Icons.TwoTone.ChevronRight,
                contentDescription = "Ver detalle",
                tint = MaterialTheme.colorScheme.outline
            )
        }
    }
}