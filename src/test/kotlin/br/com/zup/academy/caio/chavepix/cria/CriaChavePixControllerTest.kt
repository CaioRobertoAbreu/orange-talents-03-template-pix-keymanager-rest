package br.com.zup.academy.caio.chavepix.cria

import br.com.zup.academy.caio.CadastraChaveResponse
import br.com.zup.academy.caio.CriaChaveServiceGrpc
import br.com.zup.academy.caio.chavepix.factory.GrpcClientFactory
import io.grpc.StatusRuntimeException
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class CriaChavePixControllerTest{

    @field:Inject
    private lateinit var grpcClient: CriaChaveServiceGrpc.CriaChaveServiceBlockingStub

    @field:Inject
    @field:Client("/")
    private lateinit var httpClient: HttpClient

    @Test
    fun `deve registrar uma nova chave pix`(){
        //Cenario
        val pixId = UUID.randomUUID().toString()
        val clienteId = UUID.randomUUID().toString()

        val responseGrpc = CadastraChaveResponse.newBuilder()
            .setPixId(pixId)
            .setClienteId(clienteId)
            .build()

        `when`(grpcClient.registrarChave(Mockito.any()))
            .thenReturn(responseGrpc)

        val novaChavePix = CriaChavePixRequest(TipoChaveRequest.EMAIL, "email@email.com", TipoContaRequest.CONTA_CORRENTE)
        val request = HttpRequest.POST("/pix/$clienteId", novaChavePix)

        //Acao
        val response = httpClient.toBlocking().exchange(request, CriaChavePixRequest::class.java)

        //Verificacao
        assertEquals(HttpStatus.CREATED.code, response.status.code)
        assertTrue(response.headers.contains("Location"))
        assertTrue(response.header("Location")!!.contains(pixId))
    }
}

@Factory
@Replaces(factory = GrpcClientFactory::class)
internal class mockitoStubFactory{

    @Singleton
    fun stubMock(): CriaChaveServiceGrpc.CriaChaveServiceBlockingStub? {

        return Mockito.mock(CriaChaveServiceGrpc.CriaChaveServiceBlockingStub::class.java)
    }
}