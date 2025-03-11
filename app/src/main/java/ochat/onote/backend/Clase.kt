package ochat.onote.backend

import java.util.Date

data class Clase (
    val id:String,
    val name:String,
    val profesor:String,
    val date_start: Date,
    val date_end: Date
)