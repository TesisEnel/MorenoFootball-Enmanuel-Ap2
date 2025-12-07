package edu.ucne.morenofootball.data.tarjetas

import edu.ucne.morenofootball.data.tarjetas.remote.dto.request.EditTarjetaRequest
import edu.ucne.morenofootball.data.tarjetas.remote.dto.request.TarjetaRequest
import edu.ucne.morenofootball.data.tarjetas.remote.dto.response.TarjetaResponse
import edu.ucne.morenofootball.domain.tarjetas.models.Tarjeta

fun TarjetaResponse.toDomain() = Tarjeta(
    tarjetaId,
    usuarioId,
    bin,
    cvv,
    nombreTitular,
    fechaVencimiento,
    tipoTarjeta ?: ""
)

fun Tarjeta.toDto() = EditTarjetaRequest(
    tarjetaId = tarjetaId,
    usuarioId = usuarioId,
    nombreTitular = nombreTitular,
    fechaVencimiento = fechaVencimiento
)

fun Tarjeta.toCreateDto() = TarjetaRequest(
    usuarioId,
    bin,
    cvv,
    nombreTitular,
    fechaVencimiento
)