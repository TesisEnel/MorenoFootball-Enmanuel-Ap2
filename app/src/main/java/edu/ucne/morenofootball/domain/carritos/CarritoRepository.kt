package edu.ucne.morenofootball.domain.carritos

import edu.ucne.morenofootball.domain.carritos.models.request.*
import edu.ucne.morenofootball.domain.carritos.models.response.*
import edu.ucne.morenofootball.utils.Resource

interface CarritoRepository {
    suspend fun getByUsuarioId(params: UserOrSessionIdParams): Resource<CarritoResponse>
    suspend fun getTotalCarrito(params: UserOrSessionIdParams): Resource<Double>
    suspend fun agregarProducto(params: AgregarProductoParams): Resource<Unit>
    suspend fun aumentarCantidad(params: ActionWithProductFromCardParams): Resource<Unit>
    suspend fun disminuirCantidad(params: ActionWithProductFromCardParams): Resource<Unit>
    suspend fun vaciarCarrito(params: UserOrSessionIdParams): Resource<Unit>
    suspend fun deleteProduct(params: ActionWithProductFromCardParams): Resource<Unit>
}