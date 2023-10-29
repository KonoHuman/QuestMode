package com.example.questmode.ui.routine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.questmode.databinding.FragmentRoutinesBinding

class RoutinesFragment : Fragment() {

    private var _binding: FragmentRoutinesBinding? = null
    private val binding get() = _binding!!

    // Utilisation de by viewModels() pour obtenir une instance de RoutinesViewModel
    private val routinesViewModel: RoutinesViewModel by viewModels()
    private lateinit var routinesAdapter: RoutinesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoutinesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialise l'adapter et le RecyclerView
        routinesAdapter = RoutinesAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = routinesAdapter

        }

        // Observe les données des routines depuis le ViewModel
        routinesViewModel.routines.observe(viewLifecycleOwner) { routines ->
            // Mets à jour l'adapter avec les nouvelles données
            routinesAdapter.submitList(routines)
        }

        // Configure le FloatingActionButton pour ajouter une nouvelle routine
        binding.fabAddRoutine.setOnClickListener {
            showAddRoutineDialog()
        }
    }

    private fun showAddRoutineDialog() {
        val dialogFragment = AddRoutineDialogFragment()
        dialogFragment.show(parentFragmentManager, "AddRoutineDialogFragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
