package br.com.zup.academy.caio.chavepix.extensions

import br.com.zup.academy.caio.ConsultaChaveResponse
import br.com.zup.academy.caio.chavepix.consulta.DetalheChavePixResponse

fun ConsultaChaveResponse.toDetalheChavePixResponse(): DetalheChavePixResponse{

    return DetalheChavePixResponse(
        this.pixId,
        this.clienteId,
        this.tipoChave,
        this.valor,
        this.nome,
        this.cpf,
        this.instituicaoFinanceira,
        this.agencia,
        this.numero,
        this.tipoConta,
        this.criadoEm)
}
