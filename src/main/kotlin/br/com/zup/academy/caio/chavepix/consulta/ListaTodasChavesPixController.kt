package br.com.zup.academy.caio.chavepix.consulta

import br.com.zup.academy.caio.ConsultaTodasChavesGrpc
import br.com.zup.academy.caio.ListaChavesRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import org.slf4j.LoggerFactory
import javax.inject.Inject

@Controller
class ListaTodasChavesPixController(
    @Inject val grpcClient: ConsultaTodasChavesGrpc.ConsultaTodasChavesBlockingStub) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Get("/clientes/{clienteId}")
    fun consultarPorClienteId(@QueryValue clienteId: String): HttpResponse<List<ChavePixResponse>>? {

        logger.info("Realizando nova consulta de chaves do cliente")
        val request = ListaChavesRequest.newBuilder()
                            .setClienteId(clienteId)
                            .build()

        val response = grpcClient.listarTodas(request).chavesList.map { chaveResponseGrpc ->
            ChavePixResponse(chaveResponseGrpc)
        }

        logger.info("Consulta realizada com sucesso")
        return HttpResponse.ok(response)
    }
}