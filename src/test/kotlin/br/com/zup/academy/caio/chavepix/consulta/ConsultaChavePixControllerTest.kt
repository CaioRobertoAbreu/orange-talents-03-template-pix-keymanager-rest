package br.com.zup.academy.caio.chavepix.consulta

import br.com.zup.academy.caio.*
import br.com.zup.academy.caio.chavepix.factory.GrpcClientFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class ConsultaChavePixControllerTest{

    @field:Inject
    private lateinit var grpcMock: ConsultaChaveServiceGrpc.ConsultaChaveServiceBlockingStub

    @field:Inject
    @field:Client("/")
    private lateinit var httpClient: HttpClient

    @Test
    fun `deve consultar uma chave pix por ClienteId `(){
        //Cenario
        val clienteId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val porClienteId = ConsultaChaveRequest.ClienteId.newBuilder()
            .setClienteId(clienteId)
            .setPixId(pixId)
            .build()

        val requestGrpc = ConsultaChaveRequest.newBuilder()
            .setClienteId(porClienteId)
            .build()

        val responseGrpc = consultaChavePixResponse(pixId, clienteId)

        `when`(grpcMock.consultarChave(requestGrpc))
            .thenReturn(responseGrpc)

        val request = HttpRequest.GET<Any>("/clientes/${clienteId}/pix/${pixId}")

        //Acao
        val response = httpClient.toBlocking().exchange(request, DetalheChavePixResponse::class.java)

        //Verificacao
        with(response) {
            Assertions.assertEquals(HttpStatus.OK.code, response.status().code)
            Assertions.assertEquals(clienteId, response.body().clienteId)
            Assertions.assertEquals(pixId, response.body().pixId)
        }
    }

    @Test
    fun `deve consultar uma chave por Chave pix `(){
        //Cenario
        val chavePix = UUID.randomUUID().toString()

        val requestGrpc = ConsultaChaveRequest.newBuilder()
            .setChave(chavePix)
            .build()

        val responseGrpc = consultaChavePixResponse(null, null)

        `when`(grpcMock.consultarChave(requestGrpc))
            .thenReturn(responseGrpc)

        val request = HttpRequest.GET<Any>("/pix/${chavePix}")

        //Acao
        val response = httpClient.toBlocking().exchange(request, DetalheChavePixResponse::class.java)

        //Verificacao
        with(response) {
            Assertions.assertEquals(HttpStatus.OK.code, response.status().code)
            Assertions.assertNull(response.body().clienteId)
            Assertions.assertNull(response.body().pixId)
        }
    }
}

@Factory
@Replaces(factory = GrpcClientFactory::class)
internal class mockitoConsultaChaveStubFactory{

    @Singleton
    fun mockStub(): ConsultaChaveServiceGrpc.ConsultaChaveServiceBlockingStub? {

        return Mockito.mock(ConsultaChaveServiceGrpc.ConsultaChaveServiceBlockingStub::class.java)
    }

}

fun consultaChavePixResponse(pixId: String?, clienteId: String?): ConsultaChaveResponse {

    return ConsultaChaveResponse.newBuilder()
        .setPixId(pixId ?: "")
        .setClienteId(clienteId ?: "")
        .setTipoChave(TipoChave.CPF.name)
        .setValor("11388961016")
        .setNome( "Maraja dos Legados")
        .setCpf("11388961016")
        .setInstituicaoFinanceira( "Banco ficticio")
        .setAgencia("1000")
        .setNumero("0001")
        .setTipoConta(TipoConta.CONTA_CORRENTE.name)
        .setCriadoEm(LocalDateTime.now().toString())
        .build()


}