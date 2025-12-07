package edu.ucne.morenofootball.data.tarjetas.remote.dto.request

data class TarjetaRequest (
    val usuarioId: Int,
    val bin: Long,
    val cvv: Int,
    val nombreTitular: String,
    val fechaVencimiento: String,
)
