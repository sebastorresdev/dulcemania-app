package com.example.dulcemaniaapp.ui.pedido

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.dulcemaniaapp.R
import com.example.dulcemaniaapp.ViewPageAdapter
import com.google.android.material.tabs.TabLayout

class CrearPedidoFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2 : ViewPager2
    private lateinit var viewPageAdapter: ViewPageAdapter

    companion object {
        fun newInstance() = CrearPedidoFragment()
    }

    private val viewModel: CrearPedidoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_crear_pedido, container, false)

        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager2 = view.findViewById(R.id.viewPager)

        viewPageAdapter = ViewPageAdapter(this)

        viewPager2.adapter = viewPageAdapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                // Código para cuando una pestaña es seleccionada
                tab?.let {
                    viewPager2.setCurrentItem(it.position)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Código para cuando una pestaña es deseleccionada
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Código para cuando una pestaña es seleccionada de nuevo
            }
        })

        viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.getTabAt(position)?.select()
            }
        })

        return view
    }
}