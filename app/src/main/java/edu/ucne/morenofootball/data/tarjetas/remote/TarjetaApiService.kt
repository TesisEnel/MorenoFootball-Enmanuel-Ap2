package edu.ucne.morenofootball.data.tarjetas.remote

import edu.ucne.morenofootball.data.tarjetas.remote.dto.request.EditTarjetaRequest
import edu.ucne.morenofootball.data.tarjetas.remote.dto.request.TarjetaRequest
import edu.ucne.morenofootball.data.tarjetas.remote.dto.response.TarjetaResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TarjetaApiService {
    @GET("api/Tarjetas/listByUsuarioId/{usuarioId}")
    suspend fun listByUsuarioId(@Path ("usuarioId") usuarioId: Int): Response<List<TarjetaResponse>>

    @GET("api/Tarjetas/getByTarjetaId/{tarjetaId}")
    suspend fun getByTarjetaId(@Path ("tarjetaId") tarjetaId: Int): Response<TarjetaResponse>

    @POST("api/Tarjetas/create")
    suspend fun create(@Body request: TarjetaRequest): Response<TarjetaResponse>

    @PUT("api/Tarjetas/edit")
    suspend fun edit(@Body request: EditTarjetaRequest): Response<TarjetaResponse>

    @DELETE("api/Tarjetas/delete/{id}")
    suspend fun delete(@Path("id") id: Int): Response<Unit>
}