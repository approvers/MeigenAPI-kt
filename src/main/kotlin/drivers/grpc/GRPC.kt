package drivers.grpc

import apprules.MeigenUseCase
import io.grpc.Server
import io.grpc.ServerBuilder

private class MeigenService(private val usecase: MeigenUseCase) :
    MeigenAPIGrpcKt.MeigenAPICoroutineImplBase() {
    override suspend fun getByID(request: GetByIDRequest): GetByIDResponse {
        val result = usecase.getById(request.id)

        val builder = GetByIDResponse.newBuilder().setFound(result != null)

        if (result != null) {
            builder.setMeigen(
                Meigen.newBuilder()
                    .setId(result.id)
                    .setAuthor(result.author)
                    .setContent(result.content)
                    .build()
            )
        }

        return builder.build()
    }
}

class GRPCServer(private val port: Int, usecase: MeigenUseCase) {
    val server: Server = ServerBuilder.forPort(port).addService(MeigenService(usecase)).build()

    fun start() {
        server.start()
        println("GRPC server is started on port $port")
    }

    fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }
}
