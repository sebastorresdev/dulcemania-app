package com.example.dulcemaniaapp.ui.pedido

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.dulcemaniaapp.CrearPedidoActivity
import com.example.dulcemaniaapp.R

class PedidoFragment : Fragment() {

    companion object {
        fun newInstance() = PedidoFragment()
    }

    private val viewModel: PedidoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         val view = inflater.inflate(R.layout.fragment_pedido, container, false)


        val btnCrearPedido: Button = view.findViewById(R.id.btnCrearPedido)

        btnCrearPedido.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.nav_crear_pedido)
        }

        return view
    }
}