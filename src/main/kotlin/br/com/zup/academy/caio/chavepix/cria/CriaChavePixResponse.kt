package br.com.zup.academy.caio.chavepix.cria

import br.com.zup.academy.caio.TipoChave
import br.com.zup.academy.caio.TipoConta
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class CriaChavePixResponse(
    val clienteId: String,
    val pixId: String) {

}
