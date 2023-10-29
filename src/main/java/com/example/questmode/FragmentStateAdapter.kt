package com.example.questmode

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.questmode.ui.routine.RoutinesFragment
import com.example.questmode.ui.statistiques.StatisticsFragment
import com.example.questmode.ui.taches.TachesFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3  // Nombre de pages
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TachesFragment()
            1 -> RoutinesFragment()
            2 -> StatisticsFragment()
            else -> throw IllegalArgumentException("Position invalide")
        }
    }
}
