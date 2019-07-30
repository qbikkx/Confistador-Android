package dev.qbikkx.conferences.local

import androidx.room.TypeConverter

class ConfTypeConverters {

    @TypeConverter
    fun stringToList(value: String): List<String> {
        return value.split(',')
    }

    @TypeConverter
    fun listToString(value: List<String>): String {
        return value.joinToString(separator = ",")
    }
}