@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.morenofootball.ui.presentation.miCuenta

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AddCard
import androidx.compose.material.icons.twotone.CreditCard
import androidx.compose.material.icons.twotone.CreditCardOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun TarjetaModal(
    state: MiCuentaUiState,
    formattedDate: String,
    onEvent: (MiCuentaUiEvent) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    LaunchedEffect(Unit) {
        sheetState.expand()
    }

    ModalBottomSheet(
        onDismissRequest = { onEvent(MiCuentaUiEvent.ToggleTarjetasModal) },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) {
        val focusManager = LocalFocusManager.current

        PrimaryTabRow(
            selectedTabIndex = state.tabSeleccionado,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            state.tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = state.tabSeleccionado == index,
                    onClick = {
                        onEvent(MiCuentaUiEvent.OnTabChange(index))
                        onEvent(MiCuentaUiEvent.LoadTarjetas)
                    },
                    icon = {
                        Icon(
                            imageVector = if (index == 0) Icons.TwoTone.CreditCard
                            else Icons.TwoTone.AddCard,
                            contentDescription = title
                        )
                    }
                )
            }
        }
        when (state.tabSeleccionado) {
            0 -> {
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

                    state.listaTarjetas.isEmpty() -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.TwoTone.CreditCardOff,
                                contentDescription = null,
                                modifier = Modifier.size(80.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = state.errorTarjetaModal ?: "",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }

                    else -> {
                        TarjetaListBody(
                            state = state,
                            onEvent = onEvent
                        )
                    }
                }
            }

            1 -> {
                AgregarTarjetaTab(
                    state = state,
                    formattedDate = formattedDate,
                    onEvent = onEvent,
                    focusManager = focusManager
                )
            }
        }

        if (state.toggleDatePicker) {
            SimpleMonthYearPicker(
                onDateSelected = { selectedMonth, selectedYear ->
                    onEvent(
                        MiCuentaUiEvent.OnFechaVencimientoChange(
                            selectedMonth,
                            selectedYear
                        )
                    )
                },
                onDismiss = { onEvent(MiCuentaUiEvent.ToggleDatePicker) }
            )
        }

        if (state.toggleEditTarjetaDialog) {
            EditTarjetaDialog(
                state = state,
                onEvent = onEvent
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TarjetaCreditoVisualPreview() {
    TarjetaModal(
        state = MiCuentaUiState(),
        formattedDate = "2030-06",
        onEvent = {}
    )
}