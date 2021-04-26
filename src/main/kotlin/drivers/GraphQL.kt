package drivers

import apprules.MeigenUseCase
import com.expediagroup.graphql.generator.SchemaGeneratorConfig
import com.expediagroup.graphql.generator.TopLevelObject
import com.expediagroup.graphql.generator.toSchema
import entities.Meigen
import io.ktor.routing.Route
import ktor.graphql.graphQL

private class MeigenQuery(private val usecase: MeigenUseCase) {
    suspend fun byId(id: Int): Meigen? = usecase.getById(id)
}

fun Route.meigenGraphQL(path: String, usecase: MeigenUseCase): Route {
    val schema =
        toSchema(
            config = SchemaGeneratorConfig(supportedPackages = listOf("entities")),
            queries = listOf(TopLevelObject(MeigenQuery(usecase)))
        )

    return graphQL(path, schema)
}
