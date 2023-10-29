package com.example.questmode.ui.routine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.questmode.databinding.FragmentDeleteRoutineBinding

class DeleteRoutineFragment : Fragment() {

    private var _binding: FragmentDeleteRoutineBinding? = null
    private val binding get() = _binding!!
    private val routinesViewModel: RoutinesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeleteRoutineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val routineId = arguments?.getString("routineId")
        if (routineId == null) {
            // ... gestion d'erreur ...
            return
        }

        val routineLiveData = routinesViewModel.getRoutineById(routineId)

        binding.deleteButton.setOnClickListener {
            routineLiveData.value?.let { routine ->
                routinesViewModel.deleteRoutine(routine)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
