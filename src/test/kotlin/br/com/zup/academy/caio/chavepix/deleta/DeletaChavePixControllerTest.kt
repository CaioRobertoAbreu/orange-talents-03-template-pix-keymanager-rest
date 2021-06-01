package br.com.zup.academy.caio.chavepix.deleta

import br.com.zup.academy.caio.ExcluiChaveServiceGrpc
import br.com.zup.academy.caio.ExclusaoChaveRequest
import br.com.zup.academy.caio.ExclusaoChaveResponse
import br.com.zup.academy.caio.chavepix.factory.GrpcClientFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class DeletaChavePixControllerTest {

    @field:Inject
    private lateinit var grpcMock: ExcluiChaveServiceGrpc.ExcluiChaveServiceBlockingStub

    @field:Inject
    @field:Client("/")
    private lateinit var httpClient: HttpClient

    @Test
    fun `deve excluir uma chave pix existente`() {
        //Cenario
        val pixId = UUID.randomUUID().toString()
        val clienteId = UUID.randomUUID().toString()

        val requestGrpc = ExclusaoChaveRequest.newBuilder()
            .setPixId(pixId)
            .setClienteId(clienteId)
            .build()

        val responseGrpc = ExclusaoChaveResponse.newBuilder()
            .setResponse("Chave excluida com sucesso")
            .build()

        `when`(grpcMock.excluirChave(requestGrpc))
            .thenReturn(responseGrpc)

        val request = HttpRequest.DELETE("/clientes/${clienteId}/pix/${pixId}", null)

        //Acao
        val response = httpClient.toBlocking().exchange(request, Any::class.java)

        //Verificacao
        with(response){
            assertEquals(HttpStatus.NO_CONTENT.code, response.status.code)
        }
    }

}

@Factory
@Replaces(factory = GrpcClientFactory::class)
internal class mockitoExcluiChaveStubFactory{

    @Singleton
    fun mockStub(): ExcluiChaveServiceGrpc.ExcluiChaveServiceBlockingStub? {

        return Mockito.mock(ExcluiChaveServiceGrpc.ExcluiChaveServiceBlockingStub::class.java)
    }

}