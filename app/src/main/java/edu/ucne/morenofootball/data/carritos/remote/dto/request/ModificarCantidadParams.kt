package edu.ucne.morenofootball.data.carritos.remote.dto.request

data class ModificarCantidadParams(
    val usuarioId: Int?,
    val productoId: Int,
    val sesionAnonimaId: String? = null
)
