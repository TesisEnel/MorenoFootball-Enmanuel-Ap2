package edu.ucne.morenofootball.domain.carritos.models.request

data class ActionWithProductFromCardParams(
    val usuarioId: Int? = 0,
    val productoId: Int = 0,
    val sesionAnonimaId: String? = null
)
