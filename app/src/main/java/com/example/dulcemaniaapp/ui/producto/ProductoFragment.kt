package com.example.dulcemaniaapp.ui.producto

import android.app.AlertDialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dulcemaniaapp.R
import com.example.dulcemaniaapp.adapters.DetalleProductoAdapter
import com.example.dulcemaniaapp.databinding.FragmentProductoBinding
import com.example.dulcemaniaapp.models.Cliente
import com.example.dulcemaniaapp.models.DetalleProducto
import com.example.dulcemaniaapp.models.Direccion
import com.example.dulcemaniaapp.models.Producto
import com.example.dulcemaniaapp.services.ClienteService
import com.example.dulcemaniaapp.services.ProductoService
import com.example.dulcemaniaapp.ui.cliente.ClienteViewModel

class ProductoFragment : Fragment() {

    private lateinit var binding: FragmentProductoBinding
    private lateinit var productoViewModel: ProductoViewModel
    private val detalleProductos = mutableListOf<DetalleProducto>()
    private val productos = mutableListOf<Producto>()
    private lateinit var productoSeleccionado: Producto

    companion object {
        fun newInstance() = ProductoFragment()
    }

    private val viewModel: ProductoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Enlazamos con la vista
        binding = FragmentProductoBinding.inflate(inflater, container, false)

        productoViewModel = ViewModelProvider(requireActivity()).get(ProductoViewModel::class.java)

        // Obtengo los productos de la api
        obtenerProductos()

        binding.btnBuscarProducto.setOnClickListener {
            showCustomDialog()
        }

        initRecyclerView()

        return binding.root
    }

    fun initRecyclerView() {
        binding.recyclerViewProductos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewProductos.adapter = DetalleProductoAdapter(detalleProductos,
            { agregarCantidadProducto(it) }, { eliminarProducto(it) }, { disminuirCantidadProductos(it) })
    }

    private fun agregarCantidadProducto(detalleProducto: DetalleProducto) {

        val index = detalleProductos.indexOfFirst { it.id == detalleProducto.id }
        if (index != -1) {
            detalleProducto.cantidad++
            detalleProducto.precioTotal = detalleProducto.cantidad * detalleProducto.precioUnitario
            productoViewModel.actualizar(detalleProductos)
            binding.recyclerViewProductos.adapter?.notifyItemChanged(index)
        }
    }

    private fun disminuirCantidadProductos(detalleProducto: DetalleProducto) {

        val index = detalleProductos.indexOfFirst { it.id == detalleProducto.id }
        if (index != -1 && detalleProducto.cantidad > 0) {
            detalleProducto.cantidad--
            detalleProducto.precioTotal = detalleProducto.cantidad * detalleProducto.precioUnitario
            productoViewModel.actualizar(detalleProductos)
            binding.recyclerViewProductos.adapter?.notifyItemChanged(index)
        }
    }

    private fun eliminarProducto(detalleProducto: DetalleProducto) {
        val index = detalleProductos.indexOfFirst { it.id == detalleProducto.id }
        if (index != -1) {
            detalleProductos.removeAt(index)
            productoViewModel.actualizar(detalleProductos)
            binding.recyclerViewProductos.adapter?.notifyItemRemoved(index)
        }
    }

    private fun obtenerProductos() {
        // Inicializamos el servicio
        val productoService = ProductoService()

        productoService.obtenerProductos { data, error ->
            if (data != null) {

                productos.clear()
                productos.addAll(data)

            } else {
                Log.e("ERROR", error ?: "Error desconocido")
            }
        }
    }

    private fun showCustomDialog() {
        // Infla el diseño personalizado para el diálogo
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_producto, null)

        // Inicializa los componentes del diseño personalizado
        val autBuscarProducto: AutoCompleteTextView = dialogView.findViewById(R.id.autBuscarProducto)
        val etCantidad: EditText = dialogView.findViewById(R.id.etCantidad)
        val btnAgregarPedido: Button = dialogView.findViewById(R.id.btnAgregarProducto)

        ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, productos).also {
            autBuscarProducto.setAdapter(it)
        }

        autBuscarProducto.setOnItemClickListener { adapterView, view, i, l ->
            productoSeleccionado =  adapterView.getItemAtPosition(i) as Producto
        }

        // Crea y configura el AlertDialog
        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()

        // Establece la acción para el botón "Confirmar"
        btnAgregarPedido.setOnClickListener {
            val cantidad = etCantidad.text.toString().toInt()
            detalleProductos.add(DetalleProducto(productoSeleccionado.id,
                productoSeleccionado.descripcion,
                cantidad,productoSeleccionado.precioUnitario,
        productoSeleccionado.precioUnitario * cantidad))
            productoViewModel.actualizar(detalleProductos)
            alertDialog.dismiss()
        }
        alertDialog.show()
    }
}