package br.com.zup.academy.caio.chavepix.handler

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.context.annotation.Requirements
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Error
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.server.exceptions.ExceptionHandler
import org.slf4j.LoggerFactory
import javax.inject.Singleton
import javax.validation.ConstraintViolationException

@Singleton
class ChavePixExceptionHandler: ExceptionHandler<StatusRuntimeException, HttpResponse<Any>> {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun handle(request: HttpRequest<*>?, exception: StatusRuntimeException): HttpResponse<Any> {

        logger.info("Excecao ocorrida: ={}, ${exception.javaClass.name}")

        val statusCode = exception.status.code
        val statusDescription = exception.status.description ?: ""

        val (httpStatus, message) = when(statusCode){
            Status.ALREADY_EXISTS.code -> Pair(HttpStatus.UNPROCESSABLE_ENTITY, statusDescription)
            Status.INVALID_ARGUMENT.code -> Pair(HttpStatus.BAD_REQUEST, statusDescription)
            Status.FAILED_PRECONDITION.code -> Pair(HttpStatus.PRECONDITION_FAILED, statusDescription)
            Status.NOT_FOUND.code -> Pair(HttpStatus.NOT_FOUND, statusDescription)
            else -> {
                logger.error("Erro inesperado ${exception.javaClass.name}")
                Pair(HttpStatus.INTERNAL_SERVER_ERROR, "nao foi possivel completar a requisicao devido ao um " +
                        "erro inesperado")
            }
        }

        return HttpResponse.status<JsonError>(httpStatus)
            .body(JsonError(message))
    }
}
