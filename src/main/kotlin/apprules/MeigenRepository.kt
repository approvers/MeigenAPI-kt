package apprules

import entities.Meigen

interface MeigenRepository {
    suspend fun getById(id: Int): Meigen?
}
