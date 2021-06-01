package br.com.zup.academy.caio.chavepix.consulta

import br.com.zup.academy.caio.ListaChavesResponse
import io.micronaut.core.annotation.Introspected

@Introspected
class ChavePixResponse(chave: ListaChavesResponse.Chave) {

    val pixId: String = chave.pixId
    val clienteId: String = chave.clienteId
    val tipoChave: String = chave.tipoChave
    val valor: String = chave.valor
    val tipoConta: String = chave.tipoConta
    val criadoEm: String = chave.criadoEm
}