package apprules

import entities.Meigen

class MeigenUseCase(private val db: MeigenRepository) {
    suspend fun getById(id: Int): Meigen? = db.getById(id)
}
