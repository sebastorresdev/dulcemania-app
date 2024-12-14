package com.example.dulcemaniaapp

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val fragments = listOf(TabCliente(), TabProducto())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}