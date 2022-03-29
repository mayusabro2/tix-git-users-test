package id.tix.gitusers.features.domain.entity.model

import java.text.SimpleDateFormat
import java.util.*

data class User(
    val id: Long,
    val avatar_url: String,
    val email: String?,
    val html_url: String,
    val login: String,
    val created_at: String,
    val updated_at: String
) {
    fun getParsedCreatedDate() = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(created_at)
    fun getFormattedCreatedDate(): String? {
        val parsed = getParsedCreatedDate()
        return if(parsed != null) SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.SHORT).format(parsed)
        else null
    }
}