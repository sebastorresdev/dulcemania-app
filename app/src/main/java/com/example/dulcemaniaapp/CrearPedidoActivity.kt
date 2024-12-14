package com.example.dulcemaniaapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

class CrearPedidoActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2 : ViewPager2
    private  lateinit var viewPageAdapter: ViewPageAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crear_pedido)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tabLayout = findViewById(R.id.tabLayout)
        viewPager2 = findViewById(R.id.viewPager)

        //viewPageAdapter = ViewPageAdapter(this)

        //viewPager2.adapter = viewPageAdapter

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




    }
}