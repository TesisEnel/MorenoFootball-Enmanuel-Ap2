package edu.ucne.morenofootball.ui.presentation.miCuenta.tarjetas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Close
import androidx.compose.material.icons.twotone.DateRange
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material.icons.twotone.EditCalendar
import androidx.compose.material.icons.twotone.Person
import androidx.compose.material.icons.twotone.Save
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import edu.ucne.morenofootball.ui.presentation.composables.ErrorMessage
import edu.ucne.morenofootball.ui.presentation.miCuenta.MiCuentaUiEvent
import edu.ucne.morenofootball.ui.presentation.miCuenta.MiCuentaUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTarjetaDialog(
    state: MiCuentaUiState,
    onEvent: (MiCuentaUiEvent) -> Unit,
) {
    BasicAlertDialog(
        onDismissRequest = { onEvent(MiCuentaUiEvent.ToggleEditTarjetaDialog) },
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                // Titulo
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.TwoTone.Edit,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Actualizar tarjeta",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Nombre del titular
                TextField(
                    value = state.nombreTitularEditTarjeta ?: "",
                    onValueChange = { onEvent(MiCuentaUiEvent.OnNombreTitularEditTarjetaChange(it)) },
                    label = { Text("Nombre del titular") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.TwoTone.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    isError = !state.errorNombreTitularEditTarjeta.isNullOrBlank()
                )
                ErrorMessage(state.errorNombreTitularEditTarjeta)

                Spacer(modifier = Modifier.height(16.dp))

                // Fecha de vencimiento
                TextField(
                    value = ("${state.fechaVencimiento.drop(2).take(2)}/${state.fechaVencimiento.drop(5).take(2)}").ifEmpty { "MM/AA" },
                    onValueChange = { },
                    label = { Text("Fecha de vencimiento") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.TwoTone.DateRange,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    readOnly = true,
                    singleLine = true,
                    trailingIcon = {
                        IconButton(
                            onClick = { onEvent(MiCuentaUiEvent.ToggleDatePicker) }
                        ) {
                            Icon(
                                imageVector = Icons.TwoTone.EditCalendar,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    isError = !state.errorFechaVencimiento.isNullOrBlank(),
                    modifier = Modifier.fillMaxWidth()
                )
                ErrorMessage(state.errorFechaVencimiento)

                Spacer(modifier = Modifier.height(24.dp))

                // BOTONES DE ACCION
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        Button(
                            onClick = { onEvent(MiCuentaUiEvent.DeleteTarjeta(state.tarjetaSeleccionada!!.tarjetaId)) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error,
                                contentColor = MaterialTheme.colorScheme.onError
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 2.dp,
                                pressedElevation = 0.dp
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.TwoTone.Delete,
                                contentDescription = "Eliminar",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Eliminar",
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        // Boton Guardar
                        Button(
                            onClick = {
                                onEvent(MiCuentaUiEvent.OnSaveEditTarjeta)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 2.dp,
                                pressedElevation = 0.dp
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.TwoTone.Save,
                                contentDescription = "Guardar",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Guardar",
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    // Botno Cancelar
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onEvent(MiCuentaUiEvent.ToggleEditTarjetaDialog)
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.outlineVariant
                        )
                    ) {
                        Icon(
                            contentDescription = "Cancelar",
                            imageVector = Icons.TwoTone.Close,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Cancelar",
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}