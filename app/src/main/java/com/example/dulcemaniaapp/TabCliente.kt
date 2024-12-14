package com.example.dulcemaniaapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.dulcemaniaapp.viewmodels.ClienteViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TabCliente : Fragment() {

    private lateinit var bindingCliente: FragmentTabClienteBinding
    private lateinit var etCliente: EditText
    private lateinit var spinnerDirecciones: Spinner
    private lateinit var btnBuscarCliente: ImageButton
    private lateinit var clienteViewModel: ClienteViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingCliente = FragmentTabClienteBinding.inflate(inflater, container, false)

        etCliente = bindingCliente.etCliente
        spinnerDirecciones = bindingCliente.spinnerDirecciones
        btnBuscarCliente = bindingCliente.btnBuscarCliente

        clienteViewModel = ViewModelProvider(requireActivity()).get(ClienteViewModel::class.java)
        // Inflar el layout para este fragmento



        // Similar con el spinner de direcciones...
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


        btnBuscarCliente.setOnClickListener {
            // Crear el AutoCompleteTextView dentro del AlertDialog
            val autoCompleteTextView = AutoCompleteTextView(requireContext())

            // Configurar el adaptador para el AutoCompleteTextView
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, clientes)
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
                        clienteViewModel.clienteSeleccionado.value = true
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

        // Lista de direcciones de ejemplo (esto debería venir de la base de datos o de un servidor)
        val direcciones = listOf("Dirección 1", "Dirección 2", "Dirección 3", "Dirección 4")

        // Crear un ArrayAdapter para llenar el Spinner con las direcciones
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, direcciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Asignar el adaptador al Spinner
        spinnerDirecciones.adapter = adapter

        return bindingCliente.root

    }

    fun isClienteDataComplete(): Boolean {
        val clienteName = etCliente.text.toString().trim()
        // Verifica si todos los campos necesarios están completos
        return clienteName.isNotEmpty()
    }
}