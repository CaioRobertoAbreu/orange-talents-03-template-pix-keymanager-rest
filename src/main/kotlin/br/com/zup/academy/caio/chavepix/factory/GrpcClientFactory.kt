package br.com.zup.academy.caio.chavepix.factory

import br.com.zup.academy.caio.ConsultaChaveServiceGrpc
import br.com.zup.academy.caio.ConsultaTodasChavesGrpc
import br.com.zup.academy.caio.CriaChaveServiceGrpc
import br.com.zup.academy.caio.ExcluiChaveServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcClientFactory(@GrpcChannel("chavepix") val channel: ManagedChannel) {

    @Singleton
    fun criaChavePixClient() = CriaChaveServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun deleteChavePixCliente() = ExcluiChaveServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun detalhaChavePix() = ConsultaChaveServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun listaTodasChevesPix() = ConsultaTodasChavesGrpc.newBlockingStub(channel)
}