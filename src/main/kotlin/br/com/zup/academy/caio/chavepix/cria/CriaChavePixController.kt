package br.com.zup.academy.caio.chavepix.cria

import br.com.zup.academy.caio.CriaChaveServiceGrpc
import io.micronaut.core.annotation.Introspected
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.validation.Valid

@Controller
@Validated
class CriaChavePixController(
    @Inject val grpcClient: CriaChaveServiceGrpc.CriaChaveServiceBlockingStub
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Post("/clientes/{clienteId}/pix")
    fun cadastra(@QueryValue clienteId: String, @Valid @Body criaChavePixRequest: CriaChavePixRequest,
        httpRequest: HttpRequest<Any>): MutableHttpResponse<Any> {

        logger.info("Registrando uma nova chave pix")
        val request = criaChavePixRequest.toGrpcRequest(clienteId)

        val response = grpcClient.registrarChave(request)

        val uri = UriBuilder.of(httpRequest.path + "/pix/{pixId}")
            .expand(mutableMapOf("pixId" to response.pixId))

        logger.info("Chave ${response.pixId} registrada com sucesso")
        return HttpResponse.created(uri)
    }
}