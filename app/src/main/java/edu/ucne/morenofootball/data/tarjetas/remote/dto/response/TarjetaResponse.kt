package edu.ucne.morenofootball.data.tarjetas.remote.dto.response

data class TarjetaResponse(
    val tarjetaId: Int,
    val usuarioId: Int,
    val bin: Long,
    val cvv: Int,
    val nombreTitular: String,
    val fechaVencimiento: String,
    val tipoTarjeta: String?,
)
