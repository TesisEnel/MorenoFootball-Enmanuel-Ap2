package edu.ucne.morenofootball.domain.tarjetas.models

data class Tarjeta(
    val tarjetaId: Int = 0,
    val usuarioId: Int = 0,
    val bin: String = "",
    val cvv: String = "",
    val nombreTitular: String = "",
    val fechaVencimiento: String = "",
    val tipoTarjeta: String = "",
)
