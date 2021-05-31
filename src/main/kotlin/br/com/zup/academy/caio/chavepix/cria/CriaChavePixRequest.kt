package br.com.zup.academy.caio.chavepix.cria

import br.com.zup.academy.caio.CadastraChaveRequest
import br.com.zup.academy.caio.TipoChave
import br.com.zup.academy.caio.TipoConta
import br.com.zup.academy.caio.chavepix.custom_validation.ChavePix
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
@ChavePix
data class CriaChavePixRequest(
    @field:NotNull
    val tipoChave: TipoChaveRequest?,
    @field:Size(max = 77)
    val valor: String?,
    @field:NotBlank
    val tipoConta: TipoContaRequest) {

    fun toGrpcRequest(codigoInterno: String): CadastraChaveRequest? {
        return CadastraChaveRequest.newBuilder()
            .setCodigoInterno(codigoInterno)
            .setTipoChave(this.tipoChave?.atributoGrpc ?: TipoChave.CHAVE_DESCONHECIDA)
            .setValor(this.valor ?: "")
            .setTipoConta(this.tipoConta.atributoGrpc)
            .build()
    }
}

enum class TipoChaveRequest(val atributoGrpc: TipoChave) {

    CPF(TipoChave.CPF) {
        override fun valida(valor: String?): Boolean {
            if(valor.isNullOrBlank()){
                return false
            }
            return valor.matches("^[0-9]{11}$".toRegex())
        }
    },

    EMAIL(TipoChave.EMAIL) {
        override fun valida(valor: String?): Boolean {
            if(valor.isNullOrBlank()){
                return false
            }

            return valor.matches("^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$".toRegex())
        }
    },

    CELULAR(TipoChave.CELULAR) {
        override fun valida(valor: String?): Boolean {
            if(valor.isNullOrBlank()){
                return false
            }
            return valor.matches("\\+[1-9]\\d{1,14}$".toRegex())
        }
    },

    ALEATORIA(TipoChave.CHAVE_ALEATORIA) {
        override fun valida(valor: String?) = valor.isNullOrBlank()
    };

    abstract fun valida(valor: String?): Boolean
}

enum class TipoContaRequest(val atributoGrpc: TipoConta){

    CONTA_CORRENTE(TipoConta.CONTA_CORRENTE),
    CONTA_POUPANCA(TipoConta.CONTA_POUPANCA)
}
