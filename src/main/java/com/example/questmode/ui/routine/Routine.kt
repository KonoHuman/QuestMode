package com.example.questmode.ui.routine

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Routine(
    val id: String,  // Identifiant unique pour chaque routine
    val title: String,  // Titre de la routine
    val description: String,  // Description de la routine
    val hour: String,  // Heure de la routine au format HH:mm
    val priority: Priority,  // Priorité de la routine
    val repetition: Repetition,  // Répétition de la routine
    val isCompleted: Boolean,  // Statut d'achèvement de la routine
    val userId: String,
) : Parcelable

enum class Priority : Serializable {
    HIGH,
    MEDIUM,
    LOW
}

enum class Repetition : Serializable {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY,
    NONE  // Pas de répétition
}

data class WorkingHours(
    val userId: String,
    val startHour: String,
    val endHour: String
)
