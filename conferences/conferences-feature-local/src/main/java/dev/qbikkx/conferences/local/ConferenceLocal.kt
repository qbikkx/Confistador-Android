package dev.qbikkx.conferences.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conferences")
data class ConferenceLocal(
    @ColumnInfo(name = "conf_name") val name: String,
    @PrimaryKey @ColumnInfo(name = "conf_url") val url: String,
    @ColumnInfo(name = "conf_startDate") val startDate: Int,
    @ColumnInfo(name = "conf_endDate") val endDate: Int,
    @ColumnInfo(name = "conf_city") val city: String,
    @ColumnInfo(name = "conf_country") val country: String,
    @ColumnInfo(name = "conf_cfpUrl") val cfpUrl: String? = null,
    @ColumnInfo(name = "conf_cfpEndDate") val cfpEndDate: Int? = null,
    @ColumnInfo(name = "conf_twitter") val twitter: String? = null,
    @ColumnInfo(name = "categories") val categories: List<String>
)