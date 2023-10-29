package com.example.questmode.ui.taches


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.questmode.R

class TachesAdapter(
    private val onTacheClick: (Tache) -> Unit
) : ListAdapter<Tache, TachesAdapter.TachesViewHolder>(TachesDiffCallback()) {

    inner class TachesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        val description: TextView = itemView.findViewById(R.id.description)
        val dateEcheance: TextView = itemView.findViewById(R.id.dateEcheance)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onTacheClick(getItem(position))
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TachesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tache, parent, false)
        return TachesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TachesViewHolder, position: Int) {
        val currentTache = getItem(position)
        holder.checkBox.isChecked = currentTache.isCompleted
        holder.description.text = currentTache.description
        holder.dateEcheance.text = currentTache.dateEcheance.toString()
    }

    // Classe pour gérer les différences entre les listes
    class TachesDiffCallback : DiffUtil.ItemCallback<Tache>() {
        override fun areItemsTheSame(oldItem: Tache, newItem: Tache): Boolean {
            return oldItem.id == newItem.id  // Assume that Tache has an 'id' field
        }

        override fun areContentsTheSame(oldItem: Tache, newItem: Tache): Boolean {
            return oldItem == newItem
        }
    }
}
