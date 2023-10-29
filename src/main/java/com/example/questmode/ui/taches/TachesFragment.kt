package com.example.questmode.ui.taches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.questmode.R
import com.example.questmode.SpaceItemDecoration
import com.example.questmode.databinding.FragmentTachesBinding
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter

class TachesFragment : Fragment() {

    private var _binding: FragmentTachesBinding? = null
    private val tachesViewModel: TachesViewModel by viewModels()
    private lateinit var tachesAdapter: TachesAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTachesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialise l'adapter avec un handler pour le clic sur une tâche
        tachesAdapter = TachesAdapter { tache ->
            navigateToUpdateTacheFragment(tache.id)  // Appel de la méthode de navigation ici
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(SpaceItemDecoration(16))
            itemAnimator = DefaultItemAnimator()
            adapter = SlideInLeftAnimationAdapter(tachesAdapter)
        }

        // Configure un observer pour mettre à jour l'adapter lorsque les données changent
        tachesViewModel.taches.observe(viewLifecycleOwner) { tachesList ->
            tachesAdapter.submitList(tachesList)
        }

        // Charge les tâches depuis Firebase
        tachesViewModel.loadTaches()
    }

    private fun navigateToUpdateTacheFragment(tacheId: String) {
        val bundle = Bundle().apply {
            putString("tacheId", tacheId)
        }
        findNavController().navigate(R.id.updateTacheFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
