package dev.qbikkx.conferences.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
internal interface ConferencesDao {

    @Query("""
        SELECT * FROM conferences
    """)
    fun getConferences(): Observable<List<ConferenceLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveConferences(conferences: List<ConferenceLocal>): Completable
}