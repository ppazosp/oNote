package ochat.onote.backend

import org.bson.types.Binary

data class MyFile (
    val data: Binary,
    val name: String,
    val type: String,
    val classes: List<String>,
    val subject: String
)