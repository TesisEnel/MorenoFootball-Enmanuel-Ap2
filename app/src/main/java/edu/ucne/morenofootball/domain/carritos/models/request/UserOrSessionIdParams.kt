package edu.ucne.morenofootball.domain.carritos.models.request

data class UserOrSessionIdParams(
    val usuarioId: Int? = 0,
    val sesionAnonimaId: String? = null
)
