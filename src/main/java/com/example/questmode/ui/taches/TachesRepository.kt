package com.example.questmode.ui.taches

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore

class TachesRepository(private val db: FirebaseFirestore) {

    private val tachesCollection = db.collection("taches")

    private val _taches = MutableLiveData<List<Tache>>()
    val taches: LiveData<List<Tache>> = _taches

    fun loadTaches() {
        tachesCollection.get().addOnSuccessListener { result ->
            val tachesList = result.map { document ->
                document.toObject(Tache::class.java)
            }
            _taches.value = tachesList
        }
    }

    fun addTache(tache: Tache) {
        tachesCollection.add(tache)
            .addOnFailureListener { e ->
                Log.e("TachesRepository", "Failed to add tache", e)
            }
    }

    fun updateTache(tache: Tache) {
        tachesCollection.document(tache.id).set(tache)
            .addOnFailureListener { e ->
                Log.e("TachesRepository", "Failed to update tache", e)
            }
    }

    fun deleteTache(tache: Tache) {
        tachesCollection.document(tache.id).delete()
            .addOnFailureListener { e ->
                Log.e("TachesRepository", "Failed to delete tache", e)
            }
    }

    fun getTacheById(id: String): LiveData<Tache?> {
        val tacheData = MutableLiveData<Tache?>()

        db.collection("taches")
            .document(id)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val tache = document.toObject(Tache::class.java)
                    tacheData.value = tache
                } else {
                    tacheData.value = null
                }
            }
            .addOnFailureListener {
                // Gérer l'échec de la récupération de la tâche ici, si nécessaire
                tacheData.value = null
            }

        return tacheData
    }
}

