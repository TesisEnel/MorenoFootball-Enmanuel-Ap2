package edu.ucne.morenofootball.data.deseos.remote.dto.response

data class DeseoResponse(
    val deseoId: Int,
    val usuarioId: Int,
    val nombreLista: String,
    val detalles: List<DeseoDetalleResponse>
)
