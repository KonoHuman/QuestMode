package com.example.questmode.ui.statistiques

import com.github.mikephil.charting.data.BarEntry

data class StatisticsModel(
    val taskSummary: TaskSummary,
    val routineSummary: RoutineSummary,
    val preferredWorkingHoursMap: Map<String, Int>,
    val taskDistributionBarEntries: List<BarEntry>  // Changement ici

)

data class TaskSummary(
    val totalTasks: Int,
    val completedTasks: Int,
    val uncompletedTasks: Int,
    val completionRate: Float = if (totalTasks > 0) completedTasks.toFloat() / totalTasks.toFloat() else 0f,
    val formattedCompletionRate: String  // Ajouté ici
)

data class RoutineSummary(
    val totalRoutines: Int,
    val completedRoutines: Int,
    val uncompletedRoutines: Int,
    val completionRate: Float = if (totalRoutines > 0) completedRoutines.toFloat() / totalRoutines.toFloat() else 0f,
    val formattedCompletionRate: String  // Ajouté ici
)
