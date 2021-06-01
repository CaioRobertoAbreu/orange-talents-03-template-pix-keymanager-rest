package br.com.zup.academy.caio.chavepix.consulta

data class DetalheChavePixResponse(
    val pixId: String?,
    val clienteId: String?,
    val tipoChave: String,
    val valor: String,
    val nome: String,
    val cpf: String,
    val instituicao: String,
    val agencia: String,
    val numero: String,
    val tipoConta: String,
    val criadoEm: String
) {
}