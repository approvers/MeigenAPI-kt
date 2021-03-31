import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.routing.routing
import io.ktor.routing.get
import io.ktor.application.call
import io.ktor.response.respondText

fun main() {
    embeddedServer(Netty, port = 8080) {
        routing {
            get("/") {
                call.respondText("Hello, World!")
            }
        }
    }.start(wait = true)
}
