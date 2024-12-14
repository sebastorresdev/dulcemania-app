package com.example.dulcemaniaapp

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dulcemaniaapp.adapters.ProductoAdapter
import com.example.dulcemaniaapp.databinding.FragmentTabProductoBinding
import com.example.dulcemaniaapp.models.ItemProducto
import com.example.dulcemaniaapp.models.Producto
import com.example.dulcemaniaapp.services.ProductoService
import com.example.dulcemaniaapp.viewmodels.ClienteViewModel
import com.example.dulcemaniaapp.viewmodels.ProductoViewModel

class TabProducto : Fragment() {

    private lateinit var bindingProducto: FragmentTabProductoBinding
    private lateinit var clienteViewModel: ClienteViewModel

    private lateinit var productoService: ProductoService

    private var sumaTotal = 0.0
    private lateinit var btnGuardarPedido: Button
    private lateinit var recyclerProductos: RecyclerView
    private lateinit var cantidadTotal : TextView
    private lateinit var montoTotal : TextView
    private var productosSeleccionados = false

    private lateinit var productoViewModel: ProductoViewModel

    private val productosItems = mutableListOf<ItemProducto>()
    private val listaProductos = mutableListOf<Producto>()
    private lateinit var productoAdapter: ProductoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflamos el layout del fragmento
        bindingProducto = FragmentTabProductoBinding.inflate(inflater, container, false)
        clienteViewModel = ViewModelProvider(requireActivity()).get(ClienteViewModel::class.java)
        // Instanciamos el servicio de productos
        productoService = ProductoService()

        // Inicializar los componentes de la vista
        btnGuardarPedido = bindingProducto.btnGuardarPedido
        recyclerProductos = bindingProducto.recyclerProductos
        cantidadTotal = bindingProducto.tvCantidadTotal
        montoTotal = bindingProducto.tvMontoTotal

        // Inicializamos el botón deshabilitado
        btnGuardarPedido.isEnabled = false

        // Inicializar el ViewModel
        productoViewModel = ViewModelProvider(requireActivity()).get(ProductoViewModel::class.java)

        // Configurar el RecyclerView
        productoAdapter = ProductoAdapter(productosItems, productoViewModel)
        recyclerProductos.layoutManager = LinearLayoutManager(requireContext())
        recyclerProductos.adapter = productoAdapter

        // Observar el LiveData para actualizar el total
        productoViewModel.total.observe(viewLifecycleOwner) { nuevoPrecioTotal ->
            // Actualiza el total de precio en la UI
            montoTotal.text = "Precio Total: $nuevoPrecioTotal"
        }

        productoViewModel.cantidadTotal.observe(viewLifecycleOwner) { nuevaCantidadTotal ->
            // Actualiza el total de cantidad en la UI
            cantidadTotal.text = "Cantidad Total: $nuevaCantidadTotal"
        }


        // Agregamos un evento al boton buscar
        bindingProducto.btnBuscarProducto.setOnClickListener {

            // Inflar el layout del AlertDialog
            val dialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_buscar_producto, null)

            // Obtenemos referencia del AlertDialog
            val autoCompleteProducto: AutoCompleteTextView = dialogView.findViewById(R.id.autoCompleteProducto)
            val editCantidad: EditText = dialogView.findViewById(R.id.editCantidad)
            val btnAgregar: Button = dialogView.findViewById(R.id.btnAgregar)

            // Obtenemos los datos de productos del servicio
            productoService.obtenerProductos { productos, error ->

                if (productos != null) {

                    listaProductos.clear()
                    listaProductos.addAll(productos)
                    val nombreProductos: List<String> = productos.map { producto: Producto -> producto.descripcion }
                    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, nombreProductos)
                    autoCompleteProducto.setAdapter(adapter)
                } else {
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                }
            }

            // Crear el AlertDialog
            val alertDialog = AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create()

            // Mostrar el AlertDialog
            alertDialog.show()

            // Acción del botón "Agregar"
            btnAgregar.setOnClickListener {
                // Obtener los valores ingresados por el usuario
                val productoSeleccionado = autoCompleteProducto.text.toString()
                val cantidad = editCantidad.text.toString()

                // Validar que los campos no estén vacíos
                if (productoSeleccionado.isEmpty() || cantidad.isEmpty()) {
                    Toast.makeText(requireContext(), "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
                } else {

                    // obtenemos el id del producto seleccionado
                    val id = obtenerIdPorNombre(productoSeleccionado)
                    val precio = obtenerPrecioPorId(id)
                     // Instanciamos un ItemProducto
                    val itemProducto = ItemProducto(id, productoSeleccionado, cantidad.toInt(), precio)
                    productoAdapter.agregarProducto(itemProducto)

                    productoViewModel.actualizarTotales(productosItems)

                    validarGuardarPedido()
                    alertDialog.dismiss()
                }
            }
        }
        return bindingProducto.root
    }

    private fun validarGuardarPedido() {
        // Validar si todas las condiciones son verdaderas
        btnGuardarPedido.isEnabled = clienteViewModel.clienteSeleccionado.value == true &&
                clienteViewModel.direccionSeleccionada.value == true &&
                productosSeleccionados
    }

    private fun calcularCantidadTotal(item: List<ItemProducto>): Int {
        return item.map { p -> p.cantidad }.sum()
    }

    private fun obtenerPrecioPorId(id : Int) : Double {
        return listaProductos.find { p -> p.id == id }!!.precioUnitario
    }

    private fun obtenerIdPorNombre(nombre : String) : Int {
        return listaProductos.find { p -> p.descripcion ==  nombre}!!.id
    }
}
