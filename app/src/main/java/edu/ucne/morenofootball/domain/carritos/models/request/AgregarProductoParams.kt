package edu.ucne.morenofootball.domain.carritos.models.request

data class AgregarProductoParams(
    val usuarioId: Int? = 0,
    val productoId: Int = 0,
    val cantidad: Int = 1,
    val sesionAnonimaId: String? = null
)
