package com.example.questmode.ui.routine

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.questmode.R

class PriorityAdapter(context: Context) : ArrayAdapter<Priority>(context, android.R.layout.simple_spinner_item, Priority.values()) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        view.text = getItem(position)?.name ?: "N/A"  // Si getItem(position) est null, "N/A" sera utilisé comme texte.
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        view.text = getItem(position)?.name ?: "N/A"  // Si getItem(position) est null, "N/A" sera utilisé comme texte.
        return view
    }
}

class RepetitionAdapter(context: Context) : ArrayAdapter<Repetition>(context, android.R.layout.simple_spinner_item, Repetition.values()) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        view.text = getItem(position)?.name ?: "N/A"  // Si getItem(position) est null, "N/A" sera utilisé comme texte.
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        view.text = getItem(position)?.name ?: "N/A"  // Si getItem(position) est null, "N/A" sera utilisé comme texte.
        return view
    }
}


class AddRoutineDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_add_routine, container, false)

        val prioritySpinner: Spinner = view.findViewById(R.id.prioritySpinner)
        val repetitionSpinner: Spinner = view.findViewById(R.id.repetitionSpinner)

        val priorityAdapter = PriorityAdapter(requireContext())
        val repetitionAdapter = RepetitionAdapter(requireContext())

        prioritySpinner.adapter = priorityAdapter
        repetitionSpinner.adapter = repetitionAdapter

        return view
    }

    // ... autres méthodes et variables ...
}
