package com.example.dulcemaniaapp.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.dulcemaniaapp.databinding.ItemProductoBinding
import com.example.dulcemaniaapp.models.DetalleProducto

class DetalleProductoViewHolder(view: View): ViewHolder(view) {

    val binding = ItemProductoBinding.bind(view)

    fun render(detalleProducto: DetalleProducto,
               onClickListenerAdd: (DetalleProducto) -> Unit,
               onClickListenerDelete: (DetalleProducto) -> Unit,
               onClickListenerDisminuir: (DetalleProducto) -> Unit) {
        binding.tvProductoID.text = "ID: ${detalleProducto.id}"
        binding.tvProductoNombre.text = "Producto: ${detalleProducto.nombre}"
        binding.tvProductoCantidad.text = "Cantidad: ${detalleProducto.cantidad}"
        binding.tvMonto.text = "Total: S/${detalleProducto.precioTotal}"

        binding.btnAdicionar.setOnClickListener { onClickListenerAdd(detalleProducto) }
        binding.btnEliminarProducto.setOnClickListener { onClickListenerDelete(detalleProducto) }
        binding.btnDisminuir.setOnClickListener { onClickListenerDisminuir(detalleProducto) }
    }
}