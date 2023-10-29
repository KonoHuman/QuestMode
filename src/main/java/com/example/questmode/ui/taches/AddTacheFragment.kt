package com.example.questmode.ui.taches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.questmode.databinding.DialogAddTacheBinding
import com.example.questmode.ui.routine.PriorityAdapter
import com.example.questmode.ui.routine.RepetitionAdapter
import java.util.Calendar

class AddTacheFragment : Fragment() {

    private var _binding: DialogAddTacheBinding? = null
    private val binding get() = _binding!!
    private val tachesViewModel: TachesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddTacheBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val priorityAdapter = PriorityAdapter(requireContext())
        val repetitionAdapter = RepetitionAdapter(requireContext())

        binding.prioriteSpinner.adapter = priorityAdapter
        binding.repetitionSpinner.adapter = repetitionAdapter

        binding.addButton.setOnClickListener {
            val nom = binding.nomEditText.text.toString()
            val description = binding.descriptionEditText.text.toString()

            if (nom.isBlank() || description.isBlank()) {
                Toast.makeText(requireContext(), "Nom et description sont requis.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val calendrier = Calendar.getInstance().apply {
                set(binding.datePicker.year, binding.datePicker.month, binding.datePicker.dayOfMonth)
            }
            val dateEcheance = calendrier.time

            val priorite = binding.prioriteSpinner.selectedItem as? Priorite ?: Priorite.BASSE
            val repetition = binding.repetitionSpinner.selectedItem as? Repetition ?: Repetition.AUCUNE

            val calendrierCreation = Calendar.getInstance() // Suppose que la tâche a été créée maintenant
            val calendrierCompletion = Calendar.getInstance() // Suppose que la tâche a été complétée maintenant, ajuste selon ton besoin
            val userId = "tonUserId" // Remplace par la méthode correcte pour obtenir l'userId

            val tache = Tache(
                nom = nom,
                description = description,
                dateEcheance = dateEcheance,
                priorite = priorite,
                repetition = repetition,
                isCompleted = false,
                createdTimestamp = calendrierCreation.timeInMillis, // Ajouté ceci
                completedTimestamp = calendrierCompletion.timeInMillis, // Et ceci
                userId = userId // Et ceci
            )

            tachesViewModel.addTache(tache)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
