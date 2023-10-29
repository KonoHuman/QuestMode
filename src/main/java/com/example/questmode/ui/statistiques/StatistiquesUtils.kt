package com.example.questmode.ui.statistiques

import android.content.Context
import com.example.questmode.R
import com.github.mikephil.charting.data.BarEntry
import java.text.NumberFormat

object StatisticsUtils {

    /**
     * Calcule le pourcentage de tâches complétées.
     * @return le pourcentage de tâches complétées, ou null si totalTasks est 0 pour éviter la division par zéro.
     */
    fun calculateCompletionRate(completedTasks: Int, totalTasks: Int): Float? {
        return if (totalTasks > 0) (completedTasks.toFloat() / totalTasks.toFloat()) * 100 else null
    }

    /**
     * Formate le pourcentage en une chaîne lisible.
     * Utilise NumberFormat pour assurer un formatage cohérent.
     */
    fun formatPercentage(context: Context, percentage: Float?): String {
        val numberFormat = NumberFormat.getPercentInstance(context.resources.configuration.locales[0])
        numberFormat.maximumFractionDigits = 2
        return if (percentage != null) numberFormat.format(percentage / 100) else context.getString(R.string.not_available)
    }

    /**
     * Formate les heures de travail préférées pour l'affichage.
     * Utilise la StringBuilder pour une construction de chaîne plus efficace.
     */
    fun formatPreferredWorkingHours(preferredWorkingHours: Map<String, Int>): String {
        val result = StringBuilder()
        preferredWorkingHours.forEach { (key, value) ->
            result.append("$key: $value heures\n")
        }
        return result.toString().trimEnd('\n')
    }

    /**
     * Convertit une Map de données pour le graphique BarChart.
     * Utilise la déstructuration Kotlin pour un code plus lisible.
     */
    fun convertToBarEntries(data: Map<String, Int>): List<BarEntry> {
        return data.entries.mapIndexed { index, entry ->
            BarEntry(index.toFloat(), entry.value.toFloat(), entry.key)
        }
    }
}
