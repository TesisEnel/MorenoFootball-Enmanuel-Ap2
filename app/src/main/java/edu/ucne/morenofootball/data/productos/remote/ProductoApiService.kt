package edu.ucne.morenofootball.data.productos.remote

import edu.ucne.morenofootball.data.productos.remote.dto.request.ProductoRequest
import edu.ucne.morenofootball.data.productos.remote.dto.request.ProductoRequestEdit
import edu.ucne.morenofootball.data.productos.remote.dto.response.ProductoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductoApiService {
    @GET("api/Productos/listByAvability")
    suspend fun listByAvability(): Response<List<ProductoResponse>>

    @GET("api/Productos/getById/{productoId}")
    suspend fun getById(@Path("productoId") productoId: Int): Response<ProductoResponse>

    @GET("api/Productos/listByIds")
    suspend fun listByIds(@Query("idsProductos") idsProductos: List<Int>): Response<List<ProductoResponse>>

    @GET("api/Productos/listByTipo/{tipoProducto}")
    suspend fun listByTipo(@Path("tipoProducto") tipoProducto: Int): Response<List<ProductoResponse>>

    @POST("api/Productos/create")
    suspend fun save(@Body productoRequest: ProductoRequest): Response<ProductoResponse>

    @PUT("api/Productos/edit")
    suspend fun edit(@Body productoRequestEdit: ProductoRequestEdit): Response<ProductoResponse>

    @DELETE("api/Productos/delete/{productoId}")
    suspend fun delete(@Path("productoId") productoId: Int): Response<Unit>
}