package com.example.dulcemaniaapp.ui.pedido

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
import com.example.dulcemaniaapp.R
import com.example.dulcemaniaapp.databinding.FragmentClienteBinding
import com.example.dulcemaniaapp.databinding.FragmentSeleccionarClienteBinding
import com.example.dulcemaniaapp.dtos.CreatePedido
import com.example.dulcemaniaapp.models.Cliente
import com.example.dulcemaniaapp.models.Direccion
import com.example.dulcemaniaapp.services.ClienteService
import com.example.dulcemaniaapp.services.PedidoService
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class SeleccionarClienteFragment : Fragment() {

    private lateinit var binding: FragmentSeleccionarClienteBinding
    private lateinit var seleccionarClienteViewModel: SeleccionarClienteViewModel
    private lateinit var productoViewModel: ProductoViewModel
    private val clientes = mutableListOf<Cliente>()
    private val direcciones = mutableListOf<Direccion>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Enlazamos con la vista
        binding = FragmentSeleccionarClienteBinding.inflate(inflater, container, false)

        // obtenemos los clientes desde la api
        obtenerClientes()

        // Referencia al seleccionarClienteViewModel
        seleccionarClienteViewModel = ViewModelProvider(requireActivity()).get(SeleccionarClienteViewModel::class.java)

        productoViewModel = ViewModelProvider(requireActivity()).get(ProductoViewModel::class.java)

        // Registramos el evento click de button guardar pedido
        binding.btnCrearPedido.setOnClickListener {
            if (validar()) {

                val identificador = Firebase.auth.currentUser?.uid.toString()
                Log.d("VERIFICAR", "${productoViewModel.obtenerCreateDetallesPedidos()}")
                registrarPedido(
                    CreatePedido(
                    seleccionarClienteViewModel.direccionSeleccionada.value!!.id,
                    obtenerMedioPago(),
                    identificador,
                    "",
                    obtenerTipoDocumento(),
                    productoViewModel.obtenerCreateDetallesPedidos())
                )
            }
            else {
                Toast.makeText( requireContext(), "Falta ingresar datos", Toast.LENGTH_SHORT).show()
            }
        }

        // Agregamos el adaptador a la vista
        // Clientes
        ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, clientes).also {
            binding.autClientes.setAdapter(it)
        }
        binding.autClientes.setOnItemClickListener { adapterView, view, i, l ->
            val cliente =  adapterView.getItemAtPosition(i) as Cliente

            binding.autoDirecciones.setText("")
            binding.autoDirecciones.clearFocus()

            direcciones.clear()
            direcciones.addAll(cliente.direcciones)

            ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line,direcciones).also {
                binding.autoDirecciones.setAdapter(it)
            }

            seleccionarClienteViewModel.clienteSeleccionado.value = cliente
        }

        binding.autoDirecciones.setOnItemClickListener { adapterView, view, i, l ->
            val direccion =  adapterView.getItemAtPosition(i) as Direccion
            seleccionarClienteViewModel.direccionSeleccionada.value = direccion
        }

        return binding.root
    }

    private fun registrarPedido(pedido: CreatePedido) {
        Log.d("VERIFICAR", "${pedido}")
        val pedidoService = PedidoService()
        pedidoService.crearPedido(pedido) { data, error ->
            if (data != null) {
                Log.d("Datos recibidos", "${data}")
                Toast.makeText( requireContext(), "Pedido creado", Toast.LENGTH_SHORT).show()
                val navController = findNavController()
                navController.navigate(R.id.nav_pedido)
            }
            else {
                Toast.makeText( requireContext(), "No se pudo crear el pedido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun obtenerClientes() {
        // Inicializamos el servicio
        val clienteService = ClienteService()

        clienteService.obtenerClientes { data, error ->
            if (data != null) {
                clientes.clear()
                clientes.addAll(data)

            } else {
                Log.e("ERROR", error ?: "Error desconocido")
            }
        }
    }

    private fun obtenerMedioPago(): Int {
        if (binding.rbEfectivo.isChecked) {
            return 1
        }
        if (binding.rbTarjeta.isChecked) {
            return 2
        }
        return 0
    }

    private fun obtenerTipoDocumento(): String {
        if (binding.rbBoleta.isChecked) {
            return "boleta"
        }
        if (binding.rbFactura.isChecked) {
            return "factura"
        }
        return ""
    }

    private fun validar(): Boolean {
        return seleccionarClienteViewModel.clienteSeleccionado.value != null &&
                seleccionarClienteViewModel.direccionSeleccionada.value != null &&
                !productoViewModel.productosSeleccionados.value.isNullOrEmpty()
    }

}