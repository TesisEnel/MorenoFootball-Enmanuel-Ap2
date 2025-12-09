package edu.ucne.morenofootball.ui.presentation.miCuenta

import edu.ucne.morenofootball.domain.tarjetas.models.Tarjeta

data class MiCuentaUiState(
    val username: String = "",
    val tarjetaSeleccionada: Tarjeta? = null,

    val nombreTitularEditTarjeta: String? = "",

    val toggleModalTarjetas: Boolean = false,
    val toggleEditTarjetaDialog: Boolean = false,
    val toggleDatePicker: Boolean = false,

    val listaTarjetas: List<Tarjeta> = emptyList(),
    val expiryMonth: Int = 0,
    val expiryYear: Int = 0,
    val bin: String? = "",
    val cvv: String? = "",
    val nombreTitular: String? = "",
    val fechaVencimiento: String = "",

    val isLoading: Boolean = false,

    val tabSeleccionado: Int = 0,
    val tabs: List<String> = listOf("Mis Tarjetas", "Agregar Tarjeta"),

    val errorFechaVencimiento: String? = null,
    val errorNombreTitular: String? = null,
    val errorNombreTitularEditTarjeta: String? = null,
    val errorTarjetaModal: String? = "",
    val errorCvv: String? = null,
    val errorBin: String? = null,
    val errorEditTarjetaModal: String? = null,
)