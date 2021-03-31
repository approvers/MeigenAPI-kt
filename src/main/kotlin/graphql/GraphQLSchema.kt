package graphql

import com.expediagroup.graphql.generator.SchemaGeneratorConfig
import com.expediagroup.graphql.generator.TopLevelObject
import com.expediagroup.graphql.generator.toSchema
import graphql.schema.GraphQLSchema

data class Meigen(val id: Int, val author: String, val content: String)

interface MeigenDatabase {
    fun getById(id: Int): Meigen?
}

class TestMeigenDatabase : MeigenDatabase {
    override fun getById(id: Int): Meigen? = Meigen(id, "TEST_USER", "THIS_IS_MEIGEN_NO_$id")
}

class MeigenQuery(val db: MeigenDatabase) {
    fun meiegenById(id: Int): Meigen? = this.db.getById(id)
}

public fun schema(db: MeigenDatabase): GraphQLSchema = toSchema(
    config = SchemaGeneratorConfig(supportedPackages = listOf("graphql")),
    queries = listOf(TopLevelObject(MeigenQuery(db))),
)
