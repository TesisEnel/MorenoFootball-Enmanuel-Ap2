package edu.ucne.morenofootball.data.tarjetas.remote.remote.dto.response

data class TarjetaResponse(
    val tarjetaId: Int,
    val usuarioId: Int,
    val bin: String,
    val cvv: String,
    val nombreTitular: String,
    val fechaVencimiento: String,
    val tipoTarjeta: String,
)
