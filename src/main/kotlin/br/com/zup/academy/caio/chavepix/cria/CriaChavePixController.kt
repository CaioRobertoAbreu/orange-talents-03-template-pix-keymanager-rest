package br.com.zup.academy.caio.chavepix.cria

import br.com.zup.academy.caio.CriaChaveServiceGrpc
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.validation.Valid

@Controller
@Validated
class CriaChavePixController(
    @Inject val grpcClient: CriaChaveServiceGrpc.CriaChaveServiceBlockingStub
) {

    @Post("/pix/{clienteId}")
    fun cadastra(@QueryValue clienteId: String, @Valid @Body criaChavePixRequest: CriaChavePixRequest,
        httpRequest: HttpRequest<Any>): MutableHttpResponse<Any> {

        val request = criaChavePixRequest.toGrpcRequest(clienteId)

        val response = grpcClient.registrarChave(request)

        val uri = UriBuilder.of(httpRequest.path + "/pix/{pixId}")
            .expand(mutableMapOf("pixId" to response.pixId))

        return HttpResponse.created(uri)
    }
}