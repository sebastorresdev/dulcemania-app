package com.example.dulcemaniaapp.ui.cliente

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dulcemaniaapp.R
import com.example.dulcemaniaapp.adapters.ClienteAdapter
import com.example.dulcemaniaapp.adapters.PedidoAdapter
import com.example.dulcemaniaapp.databinding.FragmentClienteBinding
import com.example.dulcemaniaapp.dtos.CreatePedido
import com.example.dulcemaniaapp.models.Cliente
import com.example.dulcemaniaapp.models.Direccion
import com.example.dulcemaniaapp.services.ClienteService
import com.example.dulcemaniaapp.services.PedidoService
import com.example.dulcemaniaapp.ui.pedido.ProductoViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class ClienteFragment : Fragment() {

    private lateinit var binding: FragmentClienteBinding
    private val clientes = mutableListOf<Cliente>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Enlazamos con la vista
        binding = FragmentClienteBinding.inflate(inflater, container, false)

        // obtenemos los clientes desde la api
        obtenerClientes()


        binding.btnCrearCliente.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.nav_crear_cliente)
        }


        return binding.root
    }

    private fun initRecyclerView() {
        binding.recyclerViewClientes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewClientes.adapter = ClienteAdapter(clientes)
    }

    private fun obtenerClientes() {
        // Inicializamos el servicio
        val clienteService = ClienteService()

        clienteService.obtenerClientes { data, error ->
            if (data != null) {
                clientes.clear()
                clientes.addAll(data)
                initRecyclerView()

            } else {
                Log.e("ERROR", error ?: "Error desconocido")
            }
        }
    }
}