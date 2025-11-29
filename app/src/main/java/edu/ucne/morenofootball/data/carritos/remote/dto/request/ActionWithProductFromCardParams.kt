package edu.ucne.morenofootball.data.carritos.remote.dto.request

data class ActionWithProductFromCardParams(
    val usuarioId: Int?,
    val productoId: Int,
    val sesionAnonimaId: String? = null
)
