package edu.ucne.morenofootball.data.deseos.remote

import edu.ucne.morenofootball.data.deseos.remote.dto.request.AddProductRequest
import edu.ucne.morenofootball.data.deseos.remote.dto.request.DeseoRequest
import edu.ucne.morenofootball.data.deseos.remote.dto.response.DeseoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DeseoApiService {
    @GET("api/Deseos/listByUsuarioId/{usuarioId}")
    suspend fun listByUsuarioId(@Path("usuarioId") usuarioId: Int): Response<List<DeseoResponse>>

    @POST("/api/Deseos/create")
    suspend fun create(@Body request: DeseoRequest): Response<DeseoResponse>

    @POST("api/Deseos/add-product")
    suspend fun addProduct(@Body request: AddProductRequest): Response<DeseoResponse>

    @DELETE("api/Deseos/delete/{deseoId}")
    suspend fun delete(@Path("deseoId") deseoId: Int): Response<Unit>

    @DELETE("api/Deseos/delete-product/{detalleId}")
    suspend fun deleteProduct(@Path("detalleId") detalleId: Int): Response<Unit>
}
