package edu.ucne.morenofootball.data.tarjetas.remote.remote

import edu.ucne.morenofootball.data.carritos.remote.dto.request.AgregarProductoParams
import edu.ucne.morenofootball.data.carritos.remote.dto.request.ModificarCantidadParams
import edu.ucne.morenofootball.data.tarjetas.remote.remote.dto.request.TarjetaRequest
import edu.ucne.morenofootball.data.tarjetas.remote.remote.dto.response.TarjetaResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface TarjetaApiService {
    @GET("api/Tarjeta/listByUsuarioId")
    suspend fun listByUsuarioId(): Response<List<TarjetaResponse>>

    @POST("api/Tarjeta/create")
    suspend fun save(@Body request: TarjetaRequest): Response<TarjetaResponse>

    @PUT("api/Tarjeta/edit")
    suspend fun edit(@Body request: TarjetaRequest): Response<TarjetaResponse>

    @DELETE("api/Tarjeta/delete/{id}")
    suspend fun delete(@Path("id") id: Int): Response<Unit>
}