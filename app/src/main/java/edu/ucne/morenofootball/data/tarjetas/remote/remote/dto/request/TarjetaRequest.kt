package edu.ucne.morenofootball.data.tarjetas.remote.remote.dto.request

data class TarjetaRequest(
    val usuarioId: Int,
    val bin: String,
    val cvv: String,
    val nombreTitular: String,
    val fechaVencimiento: String,
)
