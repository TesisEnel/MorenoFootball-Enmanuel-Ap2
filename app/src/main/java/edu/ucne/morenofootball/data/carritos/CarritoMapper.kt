package edu.ucne.morenofootball.data.carritos

import edu.ucne.morenofootball.data.carritos.remote.dto.request.ActionWithProductFromCardParamsReqDto
import edu.ucne.morenofootball.data.carritos.remote.dto.request.AgregarProductoParamsReqDto
import edu.ucne.morenofootball.data.carritos.remote.dto.request.UserOrSessionIdParamsReqDto
import edu.ucne.morenofootball.data.carritos.remote.dto.response.CarritoDetalleResponseResDto
import edu.ucne.morenofootball.data.carritos.remote.dto.response.CarritoResponseResDto
import edu.ucne.morenofootball.domain.carritos.models.request.ActionWithProductFromCardParams
import edu.ucne.morenofootball.domain.carritos.models.request.AgregarProductoParams
import edu.ucne.morenofootball.domain.carritos.models.request.UserOrSessionIdParams
import edu.ucne.morenofootball.domain.carritos.models.response.CarritoDetalleResponse
import edu.ucne.morenofootball.domain.carritos.models.response.CarritoResponse

fun UserOrSessionIdParams.toDto() = UserOrSessionIdParamsReqDto(
    usuarioId = usuarioId,
    sesionAnonimaId = sesionAnonimaId
)

fun CarritoResponseResDto.toDomain() = CarritoResponse(
    carritoId,
    usuarioId,
    sesionAnonimaId,
    detalles.map { it.toDomain() }
)

fun CarritoDetalleResponseResDto.toDomain() = CarritoDetalleResponse(
    detalleId,
    carritoId,
    productoId,
    precio,
    cantidad,
    estaSeleccionado
)

fun AgregarProductoParams.toDto() = AgregarProductoParamsReqDto(
    usuarioId,
    productoId,
    cantidad,
    sesionAnonimaId
)

fun ActionWithProductFromCardParams.toDto() = ActionWithProductFromCardParamsReqDto(
    usuarioId,
    productoId,
    sesionAnonimaId
)

