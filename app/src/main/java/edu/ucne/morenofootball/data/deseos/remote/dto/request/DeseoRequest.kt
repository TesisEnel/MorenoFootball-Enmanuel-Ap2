package edu.ucne.morenofootball.data.deseos.remote.dto.request

data class DeseoRequest(
    val usuarioId: Int,
    val nombreLista: String,
    val detalles: DeseoDetalleRequest
)