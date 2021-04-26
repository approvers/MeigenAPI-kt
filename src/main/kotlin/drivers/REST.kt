package drivers

import apprules.MeigenUseCase
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route

fun Route.meigenREST(path: String, usecase: MeigenUseCase): Route =
    route(path) {
        get("id/{id}") {
            val id =
                call.parameters["id"]
                    ?: return@get call.respondText(
                        "Missing MeigenID",
                        status = HttpStatusCode.BadRequest
                    )

            val parsedId = kotlin.runCatching { id.toInt() }

            if (parsedId.isFailure) {
                return@get call.respondText(
                    "Meigen ID format is invalid. It must be valid number.",
                    status = HttpStatusCode.BadRequest
                )
            }

            val meigen =
                usecase.getById(parsedId.getOrThrow())
                    ?: return@get call.respondText(
                        "Not found such Meigen",
                        status = HttpStatusCode.NotFound
                    )

            call.respond(meigen)
        }
    }
