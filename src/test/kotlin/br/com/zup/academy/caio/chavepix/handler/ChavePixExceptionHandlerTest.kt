package br.com.zup.academy.caio.chavepix.handler

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@MicronautTest
internal class ChavePixExceptionHandlerTest {

    private val request = HttpRequest.GET<Any>("/")

    @Test
    fun `deve retornar 422 para StatunRuntimeException already exists`() {
        //Cenario
        val statusRuntimeException = StatusRuntimeException(Status.ALREADY_EXISTS
            .withDescription("valor ja cadastrado"))

        //Acao
        val error = ChavePixExceptionHandler().handle(request, statusRuntimeException)

        //Verificacao
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.code, error.status().code)
        Assertions.assertEquals("valor ja cadastrado", error.body().toString())
    }

    @Test
    fun `deve retornar 400 para StatunRuntimeException invalid argument`() {
        //Cenario
        val statusRuntimeException = StatusRuntimeException(Status.INVALID_ARGUMENT
            .withDescription("valor invalido"))

        //Acao
        val error = ChavePixExceptionHandler().handle(request, statusRuntimeException)

        //Verificacao
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.code, error.status().code)
        Assertions.assertEquals("valor invalido", error.body().toString())
    }

    @Test
    fun `deve retornar 412 para StatunRuntimeException failed precondition`() {
        //Cenario
        val statusRuntimeException = StatusRuntimeException(Status.FAILED_PRECONDITION
            .withDescription("failed precondition"))

        //Acao
        val error = ChavePixExceptionHandler().handle(request, statusRuntimeException)

        //Verificacao
        Assertions.assertEquals(HttpStatus.PRECONDITION_FAILED.code, error.status().code)
        Assertions.assertEquals("failed precondition", error.body().toString())
    }

    @Test
    fun `deve retornar 404 para StatunRuntimeException not found`() {
        //Cenario
        val statusRuntimeException = StatusRuntimeException(Status.NOT_FOUND.withDescription("nao encontrado"))

        //Acao
        val error = ChavePixExceptionHandler().handle(request, statusRuntimeException)

        //Verificacao
        Assertions.assertEquals(HttpStatus.NOT_FOUND.code, error.status().code)
        Assertions.assertEquals("nao encontrado", error.body().toString())
    }

    @Test
    fun `deve retornar 500 para erros nao esperados`() {
        //Cenario
        val statusRuntimeException = StatusRuntimeException(Status.UNKNOWN)

        //Acao
        val error = ChavePixExceptionHandler().handle(request, statusRuntimeException)

        //Verificacao
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.code, error.status().code)
        Assertions.assertEquals("nao foi possivel completar a requisicao devido ao um erro inesperado",
            error.body().toString())
    }


}