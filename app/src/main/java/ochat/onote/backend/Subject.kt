package ochat.onote.backend

import org.bson.types.Binary

data class Subject(
    val name: String,
    val photo: Binary
)