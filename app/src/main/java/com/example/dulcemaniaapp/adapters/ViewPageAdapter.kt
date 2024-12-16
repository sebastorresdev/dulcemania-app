package com.example.dulcemaniaapp.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dulcemaniaapp.ui.pedido.SeleccionarClienteFragment
import com.example.dulcemaniaapp.ui.pedido.ProductoFragment

class ViewPageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val fragments = listOf(SeleccionarClienteFragment(), ProductoFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}