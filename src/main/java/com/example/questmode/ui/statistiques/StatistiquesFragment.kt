package com.example.questmode.ui.statistiques

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.questmode.databinding.FragmentStatistiquesBinding
import com.example.questmode.databinding.LayoutGraphBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatistiquesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StatisticsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatistiquesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = "someUserId"  // Remplace cela par la méthode correcte pour obtenir l'userId
        viewModel.fetchStatistics(userId)
        val graphBinding = LayoutGraphBinding.bind(view)
        viewModel.statisticsData.observe(viewLifecycleOwner, Observer { result ->
            if (result is Result.Success) {
                updateCharts(graphBinding, result.data)
            } else if (result is Result.Error) {
                // Handle error, e.g., show a toast or error message
                Toast.makeText(context, "Error: ${result.exception.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateCharts(binding: LayoutGraphBinding, statistics: StatisticsModel) {
        binding.pieChartTasks.updateTaskSummaryChart(statistics.taskSummary)
        binding.barChartWorkingHours.updatePreferredWorkingHoursChart(statistics.preferredWorkingHoursMap)  // Ici
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private fun PieChart.updateTaskSummaryChart(taskSummary: TaskSummary) {
    val entries = listOf(
        PieEntry(taskSummary.completedTasks.toFloat(), "Tâches Complétées"),
        PieEntry(taskSummary.uncompletedTasks.toFloat(), "Tâches Non Complétées")
    )
    val dataSet = PieDataSet(entries, "Résumé des Tâches")
    this.data = PieData(dataSet)
    this.invalidate()
}

private fun BarChart.updatePreferredWorkingHoursChart(preferredWorkingHoursMap: Map<String, Int>) {
    val entries = preferredWorkingHoursMap.entries.toList().mapIndexed { index, entry ->
        BarEntry(index.toFloat(), entry.value.toFloat(), entry.key)
    }
    val dataSet = BarDataSet(entries, "Heures de Travail Préférées")
    this.data = BarData(dataSet)
    this.invalidate()
}
