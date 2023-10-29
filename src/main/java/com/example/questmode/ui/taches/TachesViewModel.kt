package com.example.questmode.ui.taches

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TachesViewModel(private val repository: TachesRepository) : ViewModel() {

    val taches: LiveData<List<Tache>> = repository.taches

    init {
        loadTaches()
    }

    fun loadTaches() {
        viewModelScope.launch {
            repository.loadTaches()
        }
    }

    fun addTache(tache: Tache) {
        viewModelScope.launch {
            repository.addTache(tache)
        }
    }

    fun updateTache(tache: Tache) {
        viewModelScope.launch {
            repository.updateTache(tache)
        }
    }

    fun deleteTache(tache: Tache) {
        viewModelScope.launch {
            repository.deleteTache(tache)
        }
    }

    fun getTacheById(id: String): LiveData<Tache?> {
        return repository.getTacheById(id)
    }
}
