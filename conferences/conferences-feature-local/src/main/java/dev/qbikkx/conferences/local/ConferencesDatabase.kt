package dev.qbikkx.conferences.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        ConferenceLocal::class
    ],
    version = 1
)
@TypeConverters(ConfTypeConverters::class)
internal abstract class ConferencesDatabase : RoomDatabase() {

    abstract fun getConferencesDao(): ConferencesDao

    companion object {

        private const val DB_NAME = "conferences.db"

        fun getInstance(context: Context) =
            Room.databaseBuilder(context, ConferencesDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}