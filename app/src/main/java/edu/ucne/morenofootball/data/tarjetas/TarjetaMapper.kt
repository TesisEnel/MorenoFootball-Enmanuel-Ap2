package edu.ucne.morenofootball.data.tarjetas

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
    tipoTarjeta
)

fun Tarjeta.toDto() = TarjetaRequest(
    usuarioId = usuarioId,
    bin = bin,
    cvv = cvv,
    nombreTitular = nombreTitular,
    fechaVencimiento =fechaVencimiento,
)