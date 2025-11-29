package edu.ucne.morenofootball.domain.productos

import edu.ucne.morenofootball.domain.productos.models.*
import edu.ucne.morenofootball.utils.Resource

interface ProductoRepository {
    suspend fun listByAvability(): Resource<List<Producto>>
    suspend fun listByTipo(tipoProducto: Int): Resource<List<Producto>>
    suspend fun save(producto: ProductoReq): Resource<Unit>
    suspend fun edit(producto: ProductoReqEdit): Resource<Unit>
    suspend fun delete(productoId: Int): Resource<Unit>
}