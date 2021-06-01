package br.com.zup.academy.caio.chavepix.deleta

import br.com.zup.academy.caio.ExcluiChaveServiceGrpc
import br.com.zup.academy.caio.ExclusaoChaveRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.QueryValue
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Inject

@Controller
@Validated
class DeletaChavePixController(
    @Inject val grpcClient: ExcluiChaveServiceGrpc.ExcluiChaveServiceBlockingStub) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Delete("/clientes/{clienteId}/pix/{pixId}")
    fun excluir(@QueryValue clienteId: String,  pixId: String): HttpResponse<Any>{

        logger.info("Excluindo uma chave pix ={${pixId}}")

        val request = ExclusaoChaveRequest.newBuilder()
            .setPixId(pixId)
            .setClienteId(clienteId)
            .build()

        grpcClient.excluirChave(request)

        logger.info("Chave ${pixId} excluida com sucesso")
        return HttpResponse.noContent()
    }
}