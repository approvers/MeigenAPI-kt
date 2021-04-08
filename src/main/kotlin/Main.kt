import kotlinx.coroutines.runBlocking

data class Meigen(
    val id: Int,
    val author: String,
    val content: String
)

interface MeigenRepository {
    suspend fun getById(id: Int): Meigen?
}

interface MeigenPresentor {
    suspend fun done(meigen: Meigen?)
}

interface GetByIdUseCase {
    suspend fun handle(id: Int)
}

class Controller(val useCase: GetByIdUseCase) {
    suspend fun getById(id: Int) = this.useCase.handle(id)
}

class MemoryMeigenRepository : MeigenRepository {
    val list: MutableList<Meigen> = mutableListOf()

    override suspend fun getById(id: Int): Meigen? = list.filter { it.id == id }.singleOrNull()
}

class ConsoleMeigenPresentor : MeigenPresentor {
    override suspend fun done(meigen: Meigen?) {
	if (meigen == null) {
	    println("そのような名言は見つかりませんでした")
	} else {
	    println("No.${meigen.id} ${meigen.author}: ${meigen.content}")
	}
    }
}

class GetByIdInteractor(val repo: MeigenRepository, val pre: MeigenPresentor) : GetByIdUseCase {
    override suspend fun handle(id: Int) =
	this.pre.done(this.repo.getById(id))
}

fun main() = runBlocking {
    while (true) {
        println("取得したい名言のIDを入力してください")

        val input = readLine()!!.toInt()

	Controller(
	    GetByIdInteractor(
		MemoryMeigenRepository(),
		ConsoleMeigenPresentor(),
	    ),
	).getById(input)
    }
}
