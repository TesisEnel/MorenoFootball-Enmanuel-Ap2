package edu.ucne.morenofootball.data.tarjetas.remote.dto.request

data class EditTarjetaRequest(
    val tarjetaId: Int = 0,
    val usuarioId: Int,
    val nombreTitular: String,
    val fechaVencimiento: String,
)
