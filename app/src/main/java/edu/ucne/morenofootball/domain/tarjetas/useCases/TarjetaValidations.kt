package edu.ucne.morenofootball.domain.tarjetas.useCases

import edu.ucne.morenofootball.utils.ValidationResult
import javax.inject.Inject

class TarjetaValidations @Inject constructor() {
    fun validateNombreTitular(nombreTitular: String): ValidationResult =
        validarNombreTitular(nombreTitular)

    fun validateBin(bin: String): ValidationResult =
        validarBin(bin)

    fun validateCvv(cvv: String): ValidationResult =
        validarCvv(cvv)

    fun validateFechaVencimiento(fechaVencimiento: String): ValidationResult =
        validarFechaVencimiento(fechaVencimiento)
}

private fun validarNombreTitular(nombreTitular: String): ValidationResult {
    if (nombreTitular.isBlank())
        return ValidationResult(
            esValido = false,
            error = "El nombre del titular no puede estar vacío *"
        )

    if (nombreTitular.length < 3)
        return ValidationResult(
            esValido = false,
            error = "El nombre del titular debe exceder de 3 caracteres *"
        )

    if (nombreTitular.length > 25)
        return ValidationResult(
            esValido = false,
            error = "El nombre del titular debe no puede exceder de 25 caracteres *"
        )

    return ValidationResult(true)
}

private fun validarBin(bin: String): ValidationResult {
    if (bin.isBlank())
        return ValidationResult(
            esValido = false,
            error = "El número de tarjeta no puede estar vacío *"
        )

    if (bin.length < 16)
        return ValidationResult(
            esValido = false,
            error = "El número de tarjeta debe exceder de 16 caracteres *"
        )

    if (bin.length > 16)
        return ValidationResult(
            esValido = false,
            error = "El número de tarjeta debe no puede exceder de 16 caracteres *"
        )

    return ValidationResult(true)
}

private fun validarCvv(cvv: String): ValidationResult {
    if (cvv.isBlank())
        return ValidationResult(
            esValido = false,
            error = "El CVV no puede estar vacío *"
        )

    if (cvv.length < 3)
        return ValidationResult(
            esValido = false,
            error = "El CVV debe exceder de 3 caracteres *"
        )

    if (cvv.length > 4)
        return ValidationResult(
            esValido = false,
            error = "El CVV debe no puede exceder de 4 caracteres *"
        )

    return ValidationResult(true)
}

private fun validarFechaVencimiento(fechaVencimiento: String): ValidationResult {
    if (fechaVencimiento.isBlank())
        return ValidationResult(
            esValido = false,
            error = "La fecha de vencimiento no puede estar vacía *"
        )
    return ValidationResult(true)
}
