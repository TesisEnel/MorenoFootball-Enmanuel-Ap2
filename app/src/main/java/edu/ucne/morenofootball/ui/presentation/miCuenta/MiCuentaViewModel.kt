package edu.ucne.morenofootball.ui.presentation.miCuenta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.morenofootball.domain.tarjetas.models.Tarjeta
import edu.ucne.morenofootball.domain.tarjetas.useCases.TarjetaUseCases
import edu.ucne.morenofootball.domain.usuarios.useCases.UsuarioUseCases
import edu.ucne.morenofootball.utils.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MiCuentaViewModel @Inject constructor(
    private val usuarioUseCases: UsuarioUseCases,
    private val tarjetaUseCaes: TarjetaUseCases,
) : ViewModel() {
    private val _state = MutableStateFlow(MiCuentaUiState())
    val state = _state.asStateFlow()

    private val _navigateToLogin = MutableSharedFlow<Unit>()
    val navigateToLogin = _navigateToLogin.asSharedFlow()

    private val _formattedDate = MutableStateFlow("")
    val formattedDate: StateFlow<String> = _formattedDate.asStateFlow()

    init {
        getUsername()
        loadTarjetas()
    }

    fun onEvent(event: MiCuentaUiEvent) {
        when (event) {
            is MiCuentaUiEvent.Logout -> logout()
            is MiCuentaUiEvent.ToggleTarjetasModal -> _state.update { it.copy(toggleModalTarjetas = !it.toggleModalTarjetas) }
            is MiCuentaUiEvent.ToggleEditTarjetaDialog -> _state.update {
                it.copy(
                    toggleEditTarjetaDialog = !it.toggleEditTarjetaDialog
                )
            }

            is MiCuentaUiEvent.GetTarjeta -> _state.update { it.copy(tarjetaSeleccionada = event.tarjeta) }
            is MiCuentaUiEvent.OnNombreTitularEditTarjetaChange -> _state.update {
                it.copy(
                    nombreTitularEditTarjeta = event.nombreTitular,
                    errorNombreTitularEditTarjeta = ""
                )
            }

            is MiCuentaUiEvent.OnBinChange -> _state.update {
                it.copy(
                    bin = event.bin,
                    errorBin = null
                )
            }

            is MiCuentaUiEvent.OnCvvChange -> _state.update {
                it.copy(
                    cvv = event.cvv,
                    errorCvv = null
                )
            }

            is MiCuentaUiEvent.OnNombreTitularChange -> _state.update {
                it.copy(
                    nombreTitular = event.nombreTitular,
                    errorNombreTitular = null
                )
            }

            is MiCuentaUiEvent.ToggleDatePicker -> _state.update { it.copy(toggleDatePicker = !it.toggleDatePicker) }
            is MiCuentaUiEvent.OnTabChange -> _state.update { it.copy(tabSeleccionado = event.tab) }
            is MiCuentaUiEvent.LoadTarjetas -> loadTarjetas()
            is MiCuentaUiEvent.OnFechaVencimientoChange -> onFechaVencimientoChange(
                event.expiryMonth,
                event.expiryYear
            )

            is MiCuentaUiEvent.OnSaveTarjeta -> saveTarjeta()
            is MiCuentaUiEvent.OnSaveEditTarjeta -> saveEditTarjeta()
            is MiCuentaUiEvent.DeleteTarjeta -> deleteTarjeta(event.tarjetaId)
        }
    }

    private fun deleteTarjeta(tarjetaId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, toggleEditTarjetaDialog = false) }

            when (val result = tarjetaUseCaes.deleteTarjetaUseCase(tarjetaId)) {
                is Resource.Success -> {
                    _state.update { it.copy(isLoading = false, toggleEditTarjetaDialog = false) }
                }
                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                is Resource.Error -> _state.update {
                    it.copy(
                        errorEditTarjetaModal = result.message,
                        isLoading = false
                    )
                }
            }
            loadTarjetas()
        }
    }

    private fun saveEditTarjeta() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val usuarioId = usuarioUseCases.getUsuarioLoggeadoUseCase()?.usuarioId ?: 0
            val tarjetaSeleccionada = _state.value.tarjetaSeleccionada
            val nombreValidation = tarjetaUseCaes.tarjetaValidations.validateNombreTitular(
                _state.value.nombreTitularEditTarjeta ?: ""
            )
            val fechaVencimientoValidation =
                tarjetaUseCaes.tarjetaValidations.validateFechaVencimiento(_state.value.fechaVencimiento)

            if (!nombreValidation.esValido ||
                !fechaVencimientoValidation.esValido ||
                tarjetaSeleccionada == null ||
                usuarioId == 0
            ) {
                _state.update {
                    it.copy(
                        errorNombreTitularEditTarjeta = nombreValidation.error,
                        errorFechaVencimiento = fechaVencimientoValidation.error,
                        errorEditTarjetaModal = "Debe haber una tarjeta seleccionada *",
                        isLoading = false
                    )
                }
                return@launch
            }

            val tarjetaEditada = Tarjeta(
                tarjetaId = tarjetaSeleccionada.tarjetaId,
                usuarioId = usuarioId,
                nombreTitular = _state.value.nombreTitularEditTarjeta ?: "",
                fechaVencimiento = _state.value.fechaVencimiento,
            )

            when (val result = tarjetaUseCaes.editTarjetaUseCase(tarjetaEditada)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            toggleEditTarjetaDialog = false,
                            nombreTitularEditTarjeta = "",
                            fechaVencimiento = "",
                            tarjetaSeleccionada = null,
                            errorFechaVencimiento = null,
                            errorEditTarjetaModal = null,
                            errorNombreTitularEditTarjeta = null,
                        )
                    }
                    loadTarjetas()
                }
                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                is Resource.Error -> _state.update {
                    it.copy(
                        isLoading = false,
                        errorEditTarjetaModal = result.message,
                    )
                }
            }
        }
    }

    private fun loadTarjetas() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val usuarioId = usuarioUseCases.getUsuarioLoggeadoUseCase()?.usuarioId ?: 0
            if (usuarioId == 0) {
                _state.update { it.copy(isLoading = false) }
                return@launch
            }

            _state.update { it.copy(isLoading = true) }
            tarjetaUseCaes.listTarjetasByUsuarioIdUsecase(usuarioId).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                listaTarjetas = result.data ?: emptyList(),
                                errorTarjetaModal = "",
                                isLoading = false,
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                errorTarjetaModal = result.message
                                    ?: "No tienes tarjetas agregadas...",
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun saveTarjeta() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val usuarioId = usuarioUseCases.getUsuarioLoggeadoUseCase()?.usuarioId ?: 0
            if (usuarioId == 0) return@launch

            val nombreValidation = tarjetaUseCaes.tarjetaValidations.validateNombreTitular(
                _state.value.nombreTitular ?: ""
            )
            val binValidation =
                tarjetaUseCaes.tarjetaValidations.validateBin(_state.value.bin ?: "")
            val cvvValidation =
                tarjetaUseCaes.tarjetaValidations.validateCvv(_state.value.cvv ?: "")
            val fechaVencimientoValidation =
                tarjetaUseCaes.tarjetaValidations.validateFechaVencimiento(_state.value.fechaVencimiento)

            if (!nombreValidation.esValido ||
                !binValidation.esValido ||
                !cvvValidation.esValido ||
                !fechaVencimientoValidation.esValido
            ) {
                _state.update {
                    it.copy(
                        errorNombreTitular = nombreValidation.error,
                        errorBin = binValidation.error,
                        errorCvv = cvvValidation.error,
                        errorFechaVencimiento = fechaVencimientoValidation.error,
                        isLoading = false
                    )
                }
                return@launch
            }

            val tarjeta = Tarjeta(
                usuarioId = usuarioId,
                bin = _state.value.bin?.toLong() ?: 0,
                cvv = _state.value.cvv?.toInt() ?: 0,
                nombreTitular = _state.value.nombreTitular ?: "",
                fechaVencimiento = _state.value.fechaVencimiento
            )

            when (val result = tarjetaUseCaes.saveTarjetaUseCase(tarjeta)) {
                is Resource.Success -> {
                    result.data?.let { nuevaTarjeta ->
                        _state.update {
                            it.copy(
                                listaTarjetas = it.listaTarjetas + nuevaTarjeta,
                                bin = "",
                                cvv = "",
                                nombreTitular = "",
                                fechaVencimiento = "",
                                errorNombreTitular = null,
                                errorBin = null,
                                errorCvv = null,
                                errorFechaVencimiento = null,
                                errorTarjetaModal = null,
                                isLoading = false,
                                toggleModalTarjetas = false
                            )
                        }
                    } ?: run {
                        _state.update {
                            it.copy(
                                errorTarjetaModal = result.message
                                    ?: "Hubo un error al guardar la tarjeta",
                                isLoading = false
                            )
                        }
                    }
                }

                is Resource.Error -> _state.update {
                    it.copy(
                        errorTarjetaModal = result.message
                            ?: "Hubo un error al guardar la tarjeta",
                        isLoading = false
                    )
                }

                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            usuarioUseCases.logoutUseCase()
            _navigateToLogin.emit(Unit)
        }
    }

    private fun getUsername() {
        viewModelScope.launch {
            usuarioUseCases.getUsuarioLoggeadoUseCase()?.let {
                _state.value = _state.value.copy(username = it.username)
            }
        }
    }

    private fun onFechaVencimientoChange(month: Int, year: Int) {
        _state.update { it.copy(errorFechaVencimiento = null) }

        val currentDate = LocalDate.now()
        val currentYear = currentDate.year
        val currentMonth = currentDate.monthValue

        val error = when {
            year < currentYear -> "El año no puede ser en el pasado *"
            year == currentYear && month < currentMonth -> "El mes no puede ser en el pasado *"
            month !in 1..12 -> "Mes inválido *"
            else -> null
        }

        val fechaVencimiento = if (error == null && month in 1..12) {
            YearMonth.of(year, month)
                .atEndOfMonth()
                .format(DateTimeFormatter.ISO_LOCAL_DATE)
        } else {
            ""
        }

        // Actualizar estado
        _state.update {
            it.copy(
                expiryMonth = month,
                expiryYear = year,
                errorFechaVencimiento = error,
                toggleDatePicker = false,
                fechaVencimiento = fechaVencimiento
            )
        }

        // Actualizar formato MM/AA
        _formattedDate.value = if (month > 0 && year > 0) {
            "${month.toString().padStart(2, '0')}/${year.toString().takeLast(2)}"
        } else ""
    }
}
