package edu.ucne.morenofootball.ui.presentation.miCuenta.tarjetas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.morenofootball.R
import edu.ucne.morenofootball.domain.tarjetas.models.Tarjeta
import edu.ucne.morenofootball.ui.presentation.miCuenta.MiCuentaUiEvent
import edu.ucne.morenofootball.ui.presentation.miCuenta.MiCuentaUiState
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TarjetaListBody(
    state: MiCuentaUiState,
    onEvent: (MiCuentaUiEvent) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(state.listaTarjetas) { tarjeta ->
            TarjetaRow(tarjeta = tarjeta, onEvent = onEvent)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TarjetaRow(
    tarjeta: Tarjeta = Tarjeta(),
    onEvent: (MiCuentaUiEvent) -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                onEvent(MiCuentaUiEvent.ToggleEditTarjetaDialog)
                onEvent(MiCuentaUiEvent.GetTarjeta(tarjeta))
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Logo de la tarjeta
            Image(
                painter = getCardLogo(tarjeta.tipoTarjeta),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Informacion de la tarjeta
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Titular
                Text(
                    text = tarjeta.nombreTitular,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                // BIN
                Text(
                    text = "•••• •••• •••• " + tarjeta.bin.toString().takeLast(4),
                    style = MaterialTheme.typography.bodyMedium,
                    letterSpacing = 1.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Fecha de vencimiento
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarToday,
                        contentDescription = "Fecha de vencimiento",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(14.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "Vence: ${formatearFechaMMyy(tarjeta.fechaVencimiento)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Indicador del tipo de tarjeta
            Badge(
                containerColor = getCardColor(tarjeta.tipoTarjeta).copy(alpha = 0.2f),
                contentColor = getCardColor(tarjeta.tipoTarjeta)
            ) {
                Text(
                    text = tarjeta.tipoTarjeta.uppercase(),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

private fun formatearFechaMMyy(fechaSinFormatear: String): String {
    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val fecha = parser.parse(fechaSinFormatear) ?: return "00/00"
        SimpleDateFormat("MM/yy", Locale.getDefault()).format(fecha)
    } catch (e: Exception) {
        "00/00"
    }
}

@Composable
private fun getCardColor(tipoTarjeta: String): Color {
    return when (tipoTarjeta.uppercase()) {
        "VISA" -> Color(0xFF1A1F71) // Azul Visa
        "MASTERCARD" -> Color(0xFFEB001B) // Rojo Mastercard
        "AMEX" -> Color(0xFF2E77BC) // Azul American Express
        "DINERS" -> Color(0xFF004C97) // Azul Diners Club
        "DISCOVER" -> Color(0xFFFF6000) // Naranja Discover
        else -> MaterialTheme.colorScheme.primary // Color por defecto del theme
    }
}

@Composable
private fun getCardLogo(tipoTarjeta: String): Painter {
    return when (tipoTarjeta.uppercase()) {
        "VISA" -> painterResource(R.drawable.visa128px)
        "MASTERCARD" -> painterResource(R.drawable.mastercard128px)
        else -> painterResource(R.drawable.no_disponible128px)
    }
}
