@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.morenofootball.ui.presentation.miCuenta.tarjetas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.ArrowBack
import androidx.compose.material.icons.automirrored.twotone.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.twotone.CreditCard
import androidx.compose.material.icons.twotone.DateRange
import androidx.compose.material.icons.twotone.EditCalendar
import androidx.compose.material.icons.twotone.Lock
import androidx.compose.material.icons.twotone.Person
import androidx.compose.material.icons.twotone.Save
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.morenofootball.ui.presentation.composables.ErrorMessage
import edu.ucne.morenofootball.ui.presentation.miCuenta.MiCuentaUiEvent
import edu.ucne.morenofootball.ui.presentation.miCuenta.MiCuentaUiState
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun AgregarTarjetaTab(
    state: MiCuentaUiState,
    formattedDate: String,
    onEvent: (MiCuentaUiEvent) -> Unit,
    focusManager: FocusManager
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    )
    {
        // TITULO
        Text(
            text = "Agregar Tarjeta de Crédito",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        TarjetaCreditoVisual(
            numero = state.bin ?: "",
            titular = state.nombreTitular ?: "",
            fecha = state.expiryMonth.toString() + state.expiryYear.toString()
        )

        // NOMBRE TITULAR
        TextField(
            value = state.nombreTitular ?: "",
            onValueChange = { onEvent(MiCuentaUiEvent.OnNombreTitularChange(it)) },
            label = { Text("Nombre del titular") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.TwoTone.Person,
                    contentDescription = "Persona"
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = state.errorNombreTitular != null,
        )
        ErrorMessage(state.errorNombreTitular)

        // BIN
        TextField(
            value = state.bin ?: "",
            onValueChange = { onEvent(MiCuentaUiEvent.OnBinChange(it.take(16))) },
            label = { Text("Número de tarjeta") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.TwoTone.CreditCard,
                    contentDescription = "Tarjeta"
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = state.errorBin != null,
        )
        ErrorMessage(state.errorBin)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                // CVV
                TextField(
                    value = state.cvv ?: "",
                    onValueChange = { onEvent(MiCuentaUiEvent.OnCvvChange(it.take(4))) },
                    label = { Text("CVV") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.TwoTone.Lock,
                            contentDescription = "Seguridad"
                        )
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    singleLine = true,
                    isError = state.errorCvv != null,
                )
                ErrorMessage(state.errorCvv)
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                // FECHA VENCIMIENOT
                TextField(
                    value = formattedDate,
                    onValueChange = { },
                    label = { Text("Fecha") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.TwoTone.DateRange,
                            contentDescription = null
                        )
                    },
                    readOnly = true,
                    singleLine = true,
                    isError = state.errorFechaVencimiento != null,
                    trailingIcon = {
                        IconButton(
                            onClick = { onEvent(MiCuentaUiEvent.ToggleDatePicker) }
                        ) {
                            Icon(
                                imageVector = Icons.TwoTone.EditCalendar,
                                contentDescription = null
                            )
                        }
                    }
                )
                ErrorMessage(state.errorFechaVencimiento)
            }
        }

        // GUARDAR
        ErrorMessage(state.errorTarjetaModal)
        Button(
            onClick = {
                onEvent(MiCuentaUiEvent.OnSaveTarjeta)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            enabled = !state.isLoading
        ) {
            Icon(
                imageVector = Icons.TwoTone.Save,
                contentDescription = "Guardar",
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Guardar")
        }
    }
}

@Composable
fun TarjetaCreditoVisual(numero: String, titular: String, fecha: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF1E1E1E), Color(0xFF424242)) // Este color es para que se vea flow premium
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp, 30.dp)
                        .background(Color(0xFFE0E0E0), RoundedCornerShape(4.dp))
                )

                Text(
                    text = if (numero.isNotEmpty()) numero.chunked(4).joinToString(" ") else "**** **** **** ****",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    letterSpacing = 2.sp,
                    fontFamily = FontFamily.Monospace
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("TITULAR", color = Color.Gray, style = MaterialTheme.typography.labelSmall)
                        Text(
                            text = titular.ifEmpty { "NOMBRE APELLIDO" }.uppercase(),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Column {
                        Text("VENCE", color = Color.Gray, style = MaterialTheme.typography.labelSmall)
                        Text(
                            text = ("${fecha.take(1)}/${fecha.drop(3)}").ifEmpty { "MM/AA" },
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun SimpleMonthYearPicker(
    onDateSelected: (month: Int, year: Int) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentDate = LocalDate.now()
    var selectedYear by remember { mutableIntStateOf(currentDate.year) }
    var selectedMonth by remember { mutableIntStateOf(currentDate.monthValue) }
    var showMonths by remember { mutableStateOf(false) }

    val months = listOf(
        "Ene", "Feb", "Mar", "Abr", "May", "Jun",
        "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"
    )

    BasicAlertDialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                // Header con año
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { selectedYear-- },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.TwoTone.ArrowBack,
                            contentDescription = "Año anterior"
                        )
                    }

                    Text(
                        text = selectedYear.toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.clickable { showMonths = true }
                    )

                    IconButton(
                        onClick = { selectedYear++ },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.TwoTone.ArrowForward,
                            contentDescription = "Año siguiente"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Grid de meses
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.height(200.dp)
                ) {
                    items(months) { monthName ->
                        val monthIndex = months.indexOf(monthName) + 1
                        val isSelected = monthIndex == selectedMonth

                        Surface(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(CircleShape),
                            color = if (isSelected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.surfaceVariant
                            },
                            onClick = {
                                selectedMonth = monthIndex
                            }
                        ) {
                            Box(
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = monthName,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = if (isSelected) {
                                        MaterialTheme.colorScheme.onPrimary
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    },
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Informacion
                val lastDay = YearMonth.of(selectedYear, selectedMonth).lengthOfMonth()
                Text(
                    text = "Vence: $lastDay/${
                        selectedMonth.toString().padStart(2, '0')
                    }/$selectedYear",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                        )
                    ) {
                        Text("Cancelar")
                    }

                    Button(
                        onClick = {
                            onDateSelected(selectedMonth, selectedYear)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Confirmar",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Seleccionar")
                    }
                }
            }
        }
    }
}