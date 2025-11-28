package edu.ucne.morenofootball.data.productos.remote.dto.request

data class ProductoRequestEdit(
    val productoId: Int = 0,
    val nombre: String = "",
    val descripcion: String = "",
    val imagenUrl: String = "",
    val precio: Double = 0.0,
    val tipoProducto: Int = 0,
    val stock: Int = 0,
)
