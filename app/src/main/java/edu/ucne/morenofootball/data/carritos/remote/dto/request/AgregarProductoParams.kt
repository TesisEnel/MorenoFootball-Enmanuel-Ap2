package edu.ucne.morenofootball.data.carritos.remote.dto.request

data class AgregarProductoParams(
    val usuarioId: Int?,
    val productoId: Int,
    val cantidad: Int = 1,
    val sesionAnonimaId: String? = null
)
