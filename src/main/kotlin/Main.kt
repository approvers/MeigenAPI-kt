import graphql.TestMeigenDatabase
import graphql.schema
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.routing.routing
import io.ktor.routing.get
import io.ktor.application.call
import io.ktor.response.respondText
import ktor.graphql.graphQL

fun main() {
    embeddedServer(Netty, port = 8080) {
        routing {
            get("/") {
                call.respondText("Hello, World!")
            }

            graphQL("/graphql", schema(TestMeigenDatabase()))
        }
    }.start(wait = true)
}
