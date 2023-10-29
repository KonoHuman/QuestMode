package com.example.questmode.ui.statistiques

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questmode.DatabaseManager
import com.example.questmode.ui.routine.Routine
import com.example.questmode.ui.taches.Priorite
import com.example.questmode.ui.taches.Tache
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(
    private val repository: StatisticsRepository,
    private val dispatcher: CoroutineDispatcher,
    private val context: Application  // Use Application context
) : ViewModel() {

    private val dbManager = DatabaseManager()
    private val _statisticsData = MutableLiveData<Result<StatisticsModel>>()
    val statisticsData: LiveData<Result<StatisticsModel>> get() = _statisticsData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchStatistics(userId: String) {
        dbManager.getTasks(userId) { tasks ->
            dbManager.getRoutines(userId) { routines ->
                _statisticsData.value = Result.Loading
                val statistics = calculateStatistics(tasks, routines)
                _statisticsData.value = Result.Success(statistics)
                viewModelScope.launch(dispatcher) {
                    try {
                        val result = repository.getTaskStatistics(userId)
                        if (result is Result.Success) {
                            _statisticsData.value = Result.Success(result.data)
                        } else if (result is Result.Error) {
                            _statisticsData.value = Result.Error(result.exception)
                            handleException(result.exception)
                        }
                    } catch (e: Exception) {
                        _statisticsData.value = Result.Error(e)
                        handleException(e)
                    }
                }
            }
        }
    }

    private fun calculateStatistics(tasks: List<Tache>, routines: List<Routine>): StatisticsModel {
        val totalTasks = tasks.size
        val completedTasks = tasks.count { it.isCompleted }
        val uncompletedTasks = totalTasks - completedTasks

        val totalRoutines = routines.size
        val completedRoutines = routines.count { it.isCompleted }
        val uncompletedRoutines = totalRoutines - completedRoutines

        val preferredWorkingHours = mutableMapOf("Morning" to 0, "Afternoon" to 0, "Evening" to 0)

        tasks.forEach { task ->
            val period = getWorkingPeriod(task.createdTimestamp)
            preferredWorkingHours[period] = preferredWorkingHours[period]?.plus(1) ?: 1
        }

        routines.forEach { routine ->
            // Conversion de l'heure en timestamp (assumant un format HH:mm) :
            val hourParts = routine.hour.split(":")
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hourParts[0].toInt())
                set(Calendar.MINUTE, hourParts[1].toInt())
            }
            val period = getWorkingPeriod(calendar.timeInMillis)  // Ajustement ici
            preferredWorkingHours[period] = preferredWorkingHours[period]?.plus(1) ?: 1
        }

        val taskDistribution = mutableMapOf<String, Int>()

        tasks.forEach { task ->
            val priority = when(task.priorite) {
                Priorite.HAUTE -> "High Priority"
                Priorite.MOYENNE -> "Medium Priority"
                Priorite.BASSE -> "Low Priority"
            }
            taskDistribution[priority] = taskDistribution.getOrDefault(priority, 0) + 1
        }

        // Utiliser StatisticsUtils pour calculer et formater les données
        val taskCompletionRate = StatisticsUtils.calculateCompletionRate(completedTasks, totalTasks)
        val routineCompletionRate = StatisticsUtils.calculateCompletionRate(completedRoutines, totalRoutines)
        val formattedTaskCompletionRate = StatisticsUtils.formatPercentage(context, taskCompletionRate)
        val formattedRoutineCompletionRate = StatisticsUtils.formatPercentage(context, routineCompletionRate)
        val taskDistributionBarEntries = StatisticsUtils.convertToBarEntries(taskDistribution)

        return StatisticsModel(
            TaskSummary(
                totalTasks,
                completedTasks,
                uncompletedTasks,
                taskCompletionRate ?: 0f,
                formattedTaskCompletionRate
            ),
            RoutineSummary(
                totalRoutines,
                completedRoutines,
                uncompletedRoutines,
                routineCompletionRate ?: 0f,
                formattedRoutineCompletionRate
            ),
            preferredWorkingHours,  // Change ceci pour passer la Map<String, Int> directement
            taskDistributionBarEntries  // Assure-toi que StatisticsModel accepte une List<BarEntry> pour ce paramètre
        )
    }

    // Une fonction hypothétique pour déterminer la période de la journée en fonction de l'heure
    private fun getWorkingPeriod(timestamp: Long): String {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
        }
        return when (calendar.get(Calendar.HOUR_OF_DAY)) {
            in 6..11 -> "Morning"
            in 12..17 -> "Afternoon"
            else -> "Evening"
        }
    }

    private fun handleException(e: Exception) {
        _errorMessage.postValue(e.message ?: "Une erreur inconnue s'est produite")
    }
}
