package edu.ucne.morenofootball.data.carritos.remote

import edu.ucne.morenofootball.data.carritos.remote.dto.request.AgregarProductoParamsReqDto
import edu.ucne.morenofootball.data.carritos.remote.dto.request.ActionWithProductFromCardParamsReqDto
import edu.ucne.morenofootball.data.carritos.remote.dto.request.UserOrSessionIdParamsReqDto
import edu.ucne.morenofootball.data.carritos.remote.dto.response.CarritoResponseResDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface CarritoApiService {
    @GET("api/Carrito/getByUsuarioId")
    suspend fun getByUsuarioId(
        @Query("usuarioId") usuarioId: Int,
        @Query("sesionAnonimaId") sesionAnonimaId: String?,
    ): Response<CarritoResponseResDto>

    @GET("api/Carrito/total")
    suspend fun getTotalCarrito(
        @Query("usuarioId") usuarioId: Int,
        @Query("sesionAnonimaId") sesionAnonimaId: String?,
    ): Response<Double>

    @POST("api/Carrito/agregar-producto")
    suspend fun agregarProducto(
        @Query("usuarioId") usuarioId: Int,
        @Query("productoId") productoId: Int,
        @Query("cantidad") cantidad: Int,
        @Query("sesionAnonimaId") sesionAnonimaId: String?,
    ): Response<Unit>

    @PUT("api/Carrito/aumentar-cantidad")
    suspend fun aumentarCantidad(
        @Query("usuarioId") usuarioId: Int,
        @Query("productoId") productoId: Int,
        @Query("sesionAnonimaId") sesionAnonimaId: String?,
    ): Response<Unit>

    @PUT("api/Carrito/disminuir-cantidad")
    suspend fun disminuirCantidad(
        @Query("usuarioId") usuarioId: Int,
        @Query("productoId") productoId: Int,
        @Query("sesionAnonimaId") sesionAnonimaId: String?,
    ): Response<Unit>

    @DELETE("api/Carrito/vaciar-carrito")
    suspend fun vaciarCarrito(
        @Query("usuarioId") usuarioId: Int,
        @Query("sesionAnonimaId") sesionAnonimaId: String?,
    ): Response<Unit>

    @DELETE("api/Carrito/delete")
    suspend fun deleteProduct(
        @Query("usuarioId") usuarioId: Int,
        @Query("productoId") productoId: Int,
        @Query("sesionAnonimaId") sesionAnonimaId: String?,
    ): Response<Unit>
}