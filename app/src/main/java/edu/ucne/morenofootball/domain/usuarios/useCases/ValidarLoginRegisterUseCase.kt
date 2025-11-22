package edu.ucne.morenofootball.domain.usuarios.useCases

import edu.ucne.morenofootball.utils.ValidationResult

class ValidarLoginRegisterUseCase {
    fun validarEmail(email: String): ValidationResult {
        if (email.isBlank()) return ValidationResult(false, "Este campo es obligatorio *")

        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")

        if (!emailRegex.matches(email))
            return ValidationResult(false, "El email no es válido *")
        return ValidationResult(true)
    }

    fun validarPassword(password: String): ValidationResult {
        if (password.isBlank())
            return ValidationResult(false, "Este campo es obligatorio *")

        if (password.length < 8)
            return ValidationResult(false, "La clave debe tener al menos 8 caracteres *")

        if (!password.any { it.isUpperCase() })
            return ValidationResult(false, "La clave debe contener al menos una letra mayúscula *")

        if (!password.any { it.isLowerCase() })
            return ValidationResult(false, "La clave debe contener al menos una letra minúscula *")

        if (!password.any { it.isDigit() })
            return ValidationResult(false, "La clave debe contener al menos un número *")

        if (!password.any { "!@#$%^&*()-_=+[]{}|;:'\",.<>?/".contains(it) }) return ValidationResult(false, "La clave debe contener al menos un carácter especial *")
        return ValidationResult(true, "La clave es segura *")
    }

    fun validarConfirmPassword(password: String, confirmPassword: String): ValidationResult {
        if (confirmPassword.isBlank())
            return ValidationResult(false, "Este campo es obligatorio *")

        if (password != confirmPassword)
            return ValidationResult(false, "Las contraseñas no coinciden *")
        return ValidationResult(true)
    }
}