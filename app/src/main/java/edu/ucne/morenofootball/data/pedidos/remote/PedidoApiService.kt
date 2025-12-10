package edu.ucne.morenofootball.data.pedidos.remote

import edu.ucne.morenofootball.data.pedidos.remote.dto.request.PedidoRequestDto
import edu.ucne.morenofootball.data.pedidos.remote.dto.response.PedidoResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PedidoApiService {
    @GET("api/Pedidos/listByUsuarioId/{usuarioId}")
    suspend fun listByUsuarioId(@Path("usuarioId") usuarioId: Int): Response<List<PedidoResponseDto>>

    @GET("api/Pedidos/getById/{pedidoId}")
    suspend fun getById(@Path("pedidoId") pedidoId: Int): Response<PedidoResponseDto>

    @GET("api/Pedidos/listByEntrega/{usuarioId}")
    suspend fun listByEntrega(@Path("usuarioId") usuarioId: Int): Response<List<PedidoResponseDto>>

    @GET("api/Pedidos/listByEnviado/{usuarioId}")
    suspend fun listByEnviado(@Path("usuarioId") usuarioId: Int): Response<List<PedidoResponseDto>>

    @POST("api/Pedidos/create")
    suspend fun create(@Body request: PedidoRequestDto): Response<Unit>
}