package br.com.zup.academy.caio.chavepix.consulta

import br.com.zup.academy.caio.ConsultaChaveRequest
import br.com.zup.academy.caio.ConsultaChaveServiceGrpc
import br.com.zup.academy.caio.chavepix.extensions.toDetalheChavePixResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Inject

@Controller
class ConsultaChavePixController(
    @Inject val grpcClient: ConsultaChaveServiceGrpc.ConsultaChaveServiceBlockingStub) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Get("/clientes/{clienteId}/pix/{pixId}")
    fun consultarPorClienteId(@QueryValue clienteId: String, pixId: String): HttpResponse<DetalheChavePixResponse>? {

        logger.info("Realizando nova consulta de chave por ClienteId")
        val porClienteId = ConsultaChaveRequest.ClienteId.newBuilder()
            .setClienteId(clienteId)
            .setPixId(pixId)
            .build()

        val request = ConsultaChaveRequest.newBuilder()
            .setClienteId(porClienteId)
            .build()

        val response = grpcClient.consultarChave(request).toDetalheChavePixResponse()

        logger.info("Consulta realizada com sucesso")
        return HttpResponse.ok(response)
    }

    @Get("/pix/{chavePix}")
    fun consultaPorChavePix(@QueryValue chavePix: String): HttpResponse<DetalheChavePixResponse>? {

        logger.info("Realizando nova consulta por Chave Pix")
        val request = ConsultaChaveRequest.newBuilder()
            .setChave(chavePix)
            .build()

        val response = grpcClient.consultarChave(request).toDetalheChavePixResponse()

        logger.info("Consulta realizada com sucesso")
        return HttpResponse.ok(response)
    }

}