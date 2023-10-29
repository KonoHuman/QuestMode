package com.example.questmode

// Fichier: DatabaseManager.kt
import com.example.questmode.ui.routine.Routine
import com.example.questmode.ui.taches.Tache
import com.google.firebase.firestore.FirebaseFirestore

class DatabaseManager {
    private val db = FirebaseFirestore.getInstance()

    fun addTask(task: Tache) {
        db.collection("tasks").document(task.id).set(task)
    }

    fun updateTaskStatus(taskId: String, isCompleted: Boolean, completedTimestamp: Long?) {
        db.collection("tasks").document(taskId).update(
            mapOf(
                "isCompleted" to isCompleted,
                "completedTimestamp" to completedTimestamp
            )
        )
    }
    fun getTasks(userId: String, callback: (List<Tache>) -> Unit) {
        db.collection("tasks")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { documents ->
                val tasks = documents.map { it.toObject(Tache::class.java) }
                callback(tasks)
            }
    }

    fun getRoutines(userId: String, callback: (List<Routine>) -> Unit) {
        db.collection("routines")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { documents ->
                val routines = documents.map { it.toObject(Routine::class.java) }
                callback(routines)
            }
    }
}
