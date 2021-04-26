package entities

import kotlinx.serialization.Serializable

@Serializable data class Meigen(val id: Int, val author: String, val content: String)
