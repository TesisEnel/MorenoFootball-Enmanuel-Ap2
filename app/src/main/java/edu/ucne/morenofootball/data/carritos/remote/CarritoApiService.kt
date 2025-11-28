package edu.ucne.morenofootball.data.carritos.remote

import edu.ucne.morenofootball.data.carritos.remote.dto.request.AgregarProductoParams
import edu.ucne.morenofootball.data.carritos.remote.dto.request.ModificarCantidadParams
import edu.ucne.morenofootball.data.carritos.remote.dto.response.CarritoResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.QueryMap

interface CarritoApiService {
    @GET("api/Carrito/getByUsuarioId")
    suspend fun listByUsuarioId(): Response<List<CarritoResponse>>

    @GET("api/Carrito/total")
    suspend fun getTotalCarrito(): Response<Double>

    @POST("api/Carrito/agregar-producto")
    suspend fun agregarProducto(@QueryMap params: AgregarProductoParams): Response<CarritoResponse>

    @PUT("api/Carrito/aumentar-Cantidad")
    suspend fun aumentarCantidad(@QueryMap params: ModificarCantidadParams): Response<CarritoResponse>

    @PUT("api/Carrito/disminuir-Cantidad")
    suspend fun disminuirCantidad(@QueryMap params: ModificarCantidadParams): Response<CarritoResponse>

    @DELETE("api/Carrito/vaciar-carrito")
    suspend fun vaciarCarrito(): Response<Unit>
}