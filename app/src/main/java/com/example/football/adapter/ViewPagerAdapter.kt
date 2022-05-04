package com.example.football.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.football.fragments.MatchesFragment
import com.example.football.fragments.TableFragment

class ViewPagerAdapter(fragment: Fragment, val id: String) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2


    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MatchesFragment.newInstance(id)
            else -> TableFragment.newInstance(id)
        }
    }


}