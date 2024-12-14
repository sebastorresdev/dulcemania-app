package com.example.dulcemaniaapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.dulcemaniaapp.databinding.FragmentTabClienteBinding
import com.example.dulcemaniaapp.models.Cliente
import com.example.dulcemaniaapp.models.Direccion
import com.example.dulcemaniaapp.models.Producto
import com.example.dulcemaniaapp.services.ClienteService
import com.example.dulcemaniaapp.viewmodels.ClienteViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TabCliente : Fragment() {

    private lateinit var bindingCliente: FragmentTabClienteBinding
    private lateinit var etCliente: EditText
    private lateinit var spinnerDirecciones: Spinner
    private lateinit var btnBuscarCliente: ImageButton
    private lateinit var clienteViewModel: ClienteViewModel

    private lateinit var clienteService: ClienteService

    private val listaClientes = mutableListOf<Cliente>()
    private val direccionClientes = mutableListOf<Direccion>()

    private lateinit var adapterSpinner: ArrayAdapter<String>

    private val direcciones = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingCliente = FragmentTabClienteBinding.inflate(inflater, container, false)

        // inicializamos el servicio
        clienteService = ClienteService()

        etCliente = bindingCliente.etCliente
        spinnerDirecciones = bindingCliente.spinnerDirecciones
        btnBuscarCliente = bindingCliente.btnBuscarCliente

        // Referencia al ClienteViewModel
        clienteViewModel = ViewModelProvider(requireActivity()).get(ClienteViewModel::class.java)

        // Evento para el spinnerDirecciones
        spinnerDirecciones.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                clienteViewModel.direccionSeleccionada.value = position != AdapterView.INVALID_POSITION
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                clienteViewModel.direccionSeleccionada.value = false
            }
        })

        // AQUI SE DEBE CONSUMIR LA API
        val clientes = listOf("Cliente 1", "Cliente 2", "Cliente 3", "Cliente 4")

        // Llamar al servicio para obtener clientes
        clienteService.obtenerClientes { clientes, error ->
            if (clientes != null) {
                // Procesar lista de clientes
                Log.d("CLIENTES", clientes.toString())
                listaClientes.clear()
                listaClientes.addAll(clientes)

            } else {
                // Manejar el error
                Log.e("ERROR", error ?: "Error desconocido")
            }
        }


        btnBuscarCliente.setOnClickListener {
            // Crear el AutoCompleteTextView dentro del AlertDialog
            val autoCompleteTextView = AutoCompleteTextView(requireContext())

            // Configurar el adaptador para el AutoCompleteTextView
            val nombreClientes = listaClientes.map { c -> c.razonSocial }

            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, nombreClientes)
            autoCompleteTextView.setAdapter(adapter)

            // Crear el AlertDialog
            val builder = MaterialAlertDialogBuilder(requireContext())
            builder.setTitle("Seleccionar Cliente")
                .setView(autoCompleteTextView)
                .setPositiveButton("Seleccionar") { dialog, which ->
                    val selectedCliente = autoCompleteTextView.text.toString()
                    if (selectedCliente.isNotEmpty()) {
                        // Mostrar el cliente seleccionado en el EditText
                        etCliente.setText(selectedCliente)
                        var d = obtenerDireccionPorNombreCliente(etCliente.text.toString()).map { d -> d.direccion }

                        direcciones.clear()
                        direcciones.addAll(d)
                        adapterSpinner.notifyDataSetChanged()
                        clienteViewModel.clienteSeleccionado.value = true

                        if (direcciones.isNotEmpty()) {
                            spinnerDirecciones.setSelection(0)
                        }

                    } else {
                        Toast.makeText(requireContext(), "Por favor selecciona un cliente", Toast.LENGTH_SHORT).show()
                        clienteViewModel.clienteSeleccionado.value = false
                    }
                }
                .setNegativeButton("Cancelar") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }

        // Crear un ArrayAdapter para llenar el Spinner con las direcciones
        adapterSpinner = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, direcciones)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Asignar el adaptador al Spinner
        spinnerDirecciones.adapter = adapterSpinner

        return bindingCliente.root

    }

    private fun obtenerDireccionPorNombreCliente(nombre:String): List<Direccion> {
        return listaClientes.find { c-> c.razonSocial == nombre }!!.direcciones.toList()
    }
}