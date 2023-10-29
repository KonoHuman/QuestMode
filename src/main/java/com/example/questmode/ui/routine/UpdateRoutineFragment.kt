package com.example.questmode.ui.routine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.questmode.databinding.FragmentUpdateRoutineBinding

class UpdateRoutineFragment : Fragment() {

    private var _binding: FragmentUpdateRoutineBinding? = null
    private val binding get() = _binding!!
    private val routinesViewModel: RoutinesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateRoutineBinding.inflate(inflater, container, false)
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

        binding.updateButton.setOnClickListener {
            routineLiveData.value?.let { routine ->
                // ... récupération des nouvelles valeurs des champs ...

                val updatedRoutine = routine.copy(
                    // ... nouvelles valeurs ...
                )

                routinesViewModel.updateRoutine(updatedRoutine)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
