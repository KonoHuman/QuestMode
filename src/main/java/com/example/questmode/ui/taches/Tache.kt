package com.example.questmode.ui.taches

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date
import java.util.*

@Parcelize
data class Tache(
    val id: String = UUID.randomUUID().toString(),
    val nom: String,
    val description: String,
    val dateEcheance: Date,
    val priorite: Priorite,
    val repetition: Repetition,
    var isCompleted: Boolean,
    val createdTimestamp: Long,
    val completedTimestamp: Long?,
    val userId: String,
) : Parcelable

enum class Priorite {
    HAUTE,
    MOYENNE,
    BASSE
}

enum class Repetition {
    QUOTIDIENNE,
    HEBDOMADAIRE,
    MENSUELLE,
    ANNUELLE,
    AUCUNE
}
