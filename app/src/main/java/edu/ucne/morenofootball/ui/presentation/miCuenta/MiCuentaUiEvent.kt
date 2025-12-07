package edu.ucne.morenofootball.ui.presentation.miCuenta

import edu.ucne.morenofootball.domain.tarjetas.models.Tarjeta

interface MiCuentaUiEvent {
    data object Logout : MiCuentaUiEvent
    data object OnMisPedidosClick : MiCuentaUiEvent
    data object OnMetodosDePagosClick : MiCuentaUiEvent
    data object ToggleTarjetasModal : MiCuentaUiEvent
    data class OnBinChange(val bin: String) : MiCuentaUiEvent
    data class OnCvvChange(val cvv: String) : MiCuentaUiEvent
    data class OnNombreTitularChange(val nombreTitular: String): MiCuentaUiEvent
    data class OnFechaVencimientoChange(val expiryMonth: Int, val expiryYear: Int) : MiCuentaUiEvent
    data object ToggleDatePicker : MiCuentaUiEvent
    data object ToggleEditTarjetaDialog : MiCuentaUiEvent
    data class GetTarjeta(val tarjeta: Tarjeta) : MiCuentaUiEvent
    data class OnNombreTitularEditTarjetaChange(val nombreTitular: String): MiCuentaUiEvent

    data object OnSaveEditTarjeta : MiCuentaUiEvent
    data class OnTabChange(val tab: Int) : MiCuentaUiEvent
    data object LoadTarjetas: MiCuentaUiEvent
    data object OnSaveTarjeta : MiCuentaUiEvent
    data class DeleteTarjeta(val tarjetaId: Int) : MiCuentaUiEvent
}