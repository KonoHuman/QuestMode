package com.example.questmode.ui.routine

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.flow

class RoutinesRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val routinesCollection = firestore.collection("routines")

    fun getRoutines() = flow {
        val snapshot = routinesCollection.get().await()
        val routines = snapshot.toObjects(Routine::class.java)
        emit(routines)
    }

    suspend fun addRoutine(routine: Routine) {
        val documentRef = routinesCollection.document()  // Crée un nouveau document avec un ID généré
        val newRoutine = routine.copy(id = documentRef.id)  // Copie la routine avec le nouvel ID
        documentRef.set(newRoutine).await()  // Enregistre la routine avec le nouvel ID
    }

    suspend fun updateRoutine(routine: Routine) {
        routine.id.let {
            routinesCollection.document(it).set(routine).await()
        }
    }

    suspend fun deleteRoutine(routine: Routine) {
        routine.id.let {
            routinesCollection.document(it).delete().await()
        }
    }

    suspend fun getRoutineById(routineId: String): Routine? {
        val docSnapshot = routinesCollection.document(routineId).get().await()
        return docSnapshot.toObject(Routine::class.java)
    }
}
