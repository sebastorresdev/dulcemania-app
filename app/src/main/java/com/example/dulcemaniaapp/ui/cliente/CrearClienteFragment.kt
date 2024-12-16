package com.example.dulcemaniaapp.ui.cliente

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.dulcemaniaapp.R
import com.example.dulcemaniaapp.adapters.ViewPageAdapter
import com.example.dulcemaniaapp.databinding.FragmentClienteBinding
import com.example.dulcemaniaapp.databinding.FragmentCrearClienteBinding
import com.example.dulcemaniaapp.databinding.FragmentCrearPedidoBinding
import com.example.dulcemaniaapp.dtos.CreateCliente
import com.example.dulcemaniaapp.dtos.CreateDireccion
import com.example.dulcemaniaapp.dtos.CreatePedido
import com.example.dulcemaniaapp.models.Cliente
import com.example.dulcemaniaapp.services.ClienteService
import com.example.dulcemaniaapp.services.PedidoService
import com.google.android.material.tabs.TabLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class CrearClienteFragment : Fragment() {

    private var _binding: FragmentCrearClienteBinding? = null
    private val binding get() = _binding!!
    private lateinit var cliente:CreateCliente

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrearClienteBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnAdd.setOnClickListener {
            if (createCliente()) {
                registraCliente(cliente)

            }
            else {
                Toast.makeText( requireContext(), "Falta ingresar datos", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun registraCliente(cliente: CreateCliente) {
        val clienteService = ClienteService()
        clienteService.crearCliente(cliente) { data, error ->
            if (data != null) {
                Log.d("Datos recibidos", "${data}")
                Toast.makeText( requireContext(), "Cliente creado", Toast.LENGTH_SHORT).show()
                val navController = findNavController()
                navController.navigate(R.id.nav_cliente)
            }
            else {
                Toast.makeText( requireContext(), "No se pudo crear el pedido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createCliente():Boolean {
        // Obtener el tipo de documento seleccionado
        val selectedRadioButtonId = binding.radioGroup.checkedRadioButtonId
        if (selectedRadioButtonId == -1) {
            Toast.makeText(context, "Seleccione un tipo de documento", Toast.LENGTH_SHORT).show()
            return false
        }
        val selectedRadioButton = binding.root.findViewById<RadioButton>(selectedRadioButtonId)
        val tipoDocumento = selectedRadioButton?.text.toString()

        // Obtener los datos de los campos
        val numeroDocumento = binding.txtDocumento.text.toString().trim()
        val razonSocial = binding.txtRazonS.text.toString().trim()
        val contacto = binding.txtContacto.text.toString().trim()
        val telefono = binding.txtTelefono.text.toString().trim()
        val correo = binding.txtCorreo.text.toString().trim()
        val direccion = binding.txtDireccion.text.toString().trim()
        val referencia = binding.txtReferencia.text.toString().trim()

        // Validar campos obligatorios
        if (numeroDocumento.isEmpty() || razonSocial.isEmpty() || contacto.isEmpty() ||
            telefono.isEmpty() || correo.isEmpty() || direccion.isEmpty() || referencia.isEmpty()) {
            Toast.makeText(context, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            return false
        }

        // Crear la lista de direcciones
        val direccionCliente = CreateDireccion(
            direccion = direccion,
            idUbigeo = "010101",
            referencia = referencia
        )

        // Crear el objeto CreateCliente
        cliente = CreateCliente(
            tipoDocumento = tipoDocumento,
            numeroDocumento = numeroDocumento,
            razonSocial = razonSocial,
            contacto = contacto,
            telefono = telefono,
            paginaWeb = "",
            correo = correo,
            direccionClientes = listOf(direccionCliente)
        )

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Liberar el binding cuando la vista se destruye
    }
}