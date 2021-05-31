package br.com.zup.academy.caio.chavepix.factory

import br.com.zup.academy.caio.CriaChaveServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcClientFactory(@GrpcChannel("chavepix") val channel: ManagedChannel) {

    @Singleton
    fun criaChavePixClient() = CriaChaveServiceGrpc.newBlockingStub(channel)

}