package com.example.questmode.ui.statistiques

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlin.Exception

interface StatisticsRepository {
    suspend fun getTaskStatistics(userId: String): Result<StatisticsModel>
    suspend fun saveTaskStatistics(userId: String, taskStatistics: StatisticsModel): Result<Void?>
}

class FirebaseStatisticsRepository : StatisticsRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val statisticsCollection = firestore.collection("statistics")

    override suspend fun getTaskStatistics(userId: String): Result<StatisticsModel> {
        return try {
            val document = statisticsCollection.document(userId).get().await()
            val statistics = document.toObject(StatisticsModel::class.java)
                ?: throw Exception("Document not found or failed to parse")
            Result.Success(statistics)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun saveTaskStatistics(userId: String, taskStatistics: StatisticsModel): Result<Void?> {
        return try {
            statisticsCollection.document(userId).set(taskStatistics).await()
            Result.Success(null)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
