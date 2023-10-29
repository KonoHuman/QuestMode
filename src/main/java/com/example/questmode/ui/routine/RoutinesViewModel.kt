package com.example.questmode.ui.routine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RoutinesViewModel(private val repository: RoutinesRepository) : ViewModel() {

    // LiveData pour observer les routines depuis le fragment
    val routines: LiveData<List<Routine>> = repository.getRoutines().asLiveData()

    // Méthode pour ajouter une nouvelle routine
    fun addRoutine(routine: Routine) {
        viewModelScope.launch {
            repository.addRoutine(routine)
        }
    }

    // Méthode pour mettre à jour une routine existante
    fun updateRoutine(routine: Routine) {
        viewModelScope.launch {
            repository.updateRoutine(routine)
        }
    }

    // Méthode pour supprimer une routine
    fun deleteRoutine(routine: Routine) {
        viewModelScope.launch {
            repository.deleteRoutine(routine)
        }
    }

    fun getRoutineById(routineId: String): LiveData<Routine?> {
        val routineLiveData = MutableLiveData<Routine?>()
        viewModelScope.launch {
            val routine = repository.getRoutineById(routineId)
            routineLiveData.value = routine  // Pas d'erreur ici car routineLiveData accepte les valeurs nullables
        }
        return routineLiveData
    }
}

