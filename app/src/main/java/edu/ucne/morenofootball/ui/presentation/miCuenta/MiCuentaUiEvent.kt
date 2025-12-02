package edu.ucne.morenofootball.ui.presentation.miCuenta

interface MiCuentaUiEvent {
    data object Logout : MiCuentaUiEvent
    data object OnOrdersClick : MiCuentaUiEvent
    data object OnPaymentMethodsClick : MiCuentaUiEvent
}