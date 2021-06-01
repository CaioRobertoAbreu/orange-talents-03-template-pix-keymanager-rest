package br.com.zup.academy.caio.chavepix.consulta

import br.com.zup.academy.caio.*
import br.com.zup.academy.caio.chavepix.factory.GrpcClientFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class ListaTodasChavesPixControllerTest{

    @field:Inject
    private lateinit var grpcMock: ConsultaTodasChavesGrpc.ConsultaTodasChavesBlockingStub

    @field:Inject
    @field:Client("/")
    private lateinit var httpClient: HttpClient

    @Test
    fun `deve consultar uma chave pix por ClienteId `(){
        //Cenario
        val clienteId = UUID.randomUUID().toString()

        val requestGrpc = ListaChavesRequest.newBuilder()
            .setClienteId(clienteId)
            .build()

        val responseGrpc = criaResponseGrpc(clienteId)

        Mockito.`when`(grpcMock.listarTodas(requestGrpc))
            .thenReturn(responseGrpc)

        val request = HttpRequest.GET<Any>("/clientes/${clienteId}")

        //Acao
        val response = httpClient.toBlocking().exchange(request, List::class.java)

        //Verificacao
        with(response) {
            Assertions.assertEquals(HttpStatus.OK.code, response.status.code)
            Assertions.assertNotNull(response.body())
            Assertions.assertEquals(2, response.body().count())
        }
    }

}

@Factory
@Replaces(factory = GrpcClientFactory::class)
internal class mockitoListaChaveStubFactory{

    @Singleton
    fun mockStub(): ConsultaTodasChavesGrpc.ConsultaTodasChavesBlockingStub? {

        return Mockito.mock(ConsultaTodasChavesGrpc.ConsultaTodasChavesBlockingStub::class.java)
    }

}

fun criaResponseGrpc(clienteId: String): ListaChavesResponse {

    val chave1 = ListaChavesResponse.Chave.newBuilder()
        .setPixId(UUID.randomUUID().toString())
        .setClienteId(clienteId)
        .setTipoChave(TipoChave.CHAVE_ALEATORIA.name)
        .setValor(UUID.randomUUID().toString())
        .setTipoConta(TipoConta.CONTA_CORRENTE.name)
        .setCriadoEm(LocalDateTime.now().toString())
        .build()

    val chave2 = ListaChavesResponse.Chave.newBuilder()
        .setPixId(UUID.randomUUID().toString())
        .setClienteId(clienteId)
        .setTipoChave(TipoChave.CPF.name)
        .setValor("35392534082")
        .setTipoConta(TipoConta.CONTA_CORRENTE.name)
        .setCriadoEm(LocalDateTime.now().toString())
        .build()

    return ListaChavesResponse.newBuilder()
        .addChaves(chave1)
        .addChaves(chave2)
        .build()
}