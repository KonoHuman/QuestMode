package com.example.questmode.ui.routine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.questmode.R
import com.example.questmode.databinding.ItemRoutineBinding  // Assure-toi de générer ce fichier de binding

class RoutinesAdapter : ListAdapter<Routine, RoutinesAdapter.RoutineViewHolder>(RoutineDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val binding = ItemRoutineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoutineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val routine = getItem(position)
        holder.bind(routine)
    }

    class RoutineViewHolder(private val binding: ItemRoutineBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(routine: Routine) {
            binding.titleTextView.text = routine.title
            binding.descriptionTextView.text = routine.description
            binding.hourTextView.text = "Heure: ${routine.hour}"
            binding.priorityTextView.text = "Priorité: ${routine.priority.name}"
            binding.repetitionTextView.text = "Répétition: ${routine.repetition.name}"
            binding.isCompletedCheckBox.isChecked = routine.isCompleted
        }
    }

    class RoutineDiffCallback : DiffUtil.ItemCallback<Routine>() {
        override fun areItemsTheSame(oldItem: Routine, newItem: Routine): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Routine, newItem: Routine): Boolean {
            return oldItem == newItem
        }
    }
}
