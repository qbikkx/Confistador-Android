package dev.qbikkx.conferences.remote.source

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import dev.qbikkx.conferences.remote.api.ConferencesApi
import dev.qbikkx.conferences.remote.model.ConferenceRemote
import io.reactivex.Single
import javax.inject.Inject

internal class ConferencesRemoteDataSourceImpl @Inject constructor(
    private val conferencesApi: ConferencesApi,
    private val firestore: FirebaseFirestore
) : ConferencesRemoteDataSource {

    override fun getConferences() = Single.create<List<ConferenceRemote>> { emmiter ->
        firestore.collection("conference")
            .get()
            .addOnSuccessListener { querySnapshot ->
                emmiter.onSuccess(querySnapshot.toObjects<ConferenceRemote>())
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }
    }
}