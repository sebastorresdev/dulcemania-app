package com.example.dulcemaniaapp.ui.pedido

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dulcemaniaapp.R
import com.example.dulcemaniaapp.adapters.PedidoAdapter
import com.example.dulcemaniaapp.databinding.FragmentPedidoBinding
import com.example.dulcemaniaapp.models.Pedido
import com.example.dulcemaniaapp.services.PedidoService

class PedidoFragment : Fragment() {

    private lateinit var binding: FragmentPedidoBinding
    private val pedidos = mutableListOf<Pedido>()

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
        // Enlazamos con la vista
        binding = FragmentPedidoBinding.inflate(inflater, container, false)

        // Obtenemos los pedidos
        obtenerPedidos()

        binding.btnCrearPedido.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.nav_crear_pedido)
        }

        return binding.root
    }

    fun obtenerPedidos() {
        val pedidoService = PedidoService()

        pedidoService.obtenerPedidos() { data, error ->
            if (data != null) {
                // Procesar lista de clientes
                //Log.d("CLIENTES", clientes.toString())
                pedidos.clear()
                pedidos.addAll(data)
                initRecyclerView()
                Log.d("PEDIDOS", "DATA: ${data.toString()}")
            } else {
                Log.e("ERROR", error ?: "Error desconocido")
            }
        }
    }

    fun initRecyclerView() {
        binding.recyclerViewPedidos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewPedidos.adapter = PedidoAdapter(pedidos)
    }
}