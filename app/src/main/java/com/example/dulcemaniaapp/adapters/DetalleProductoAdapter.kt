package com.example.dulcemaniaapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dulcemaniaapp.R
import com.example.dulcemaniaapp.models.DetalleProducto

class DetalleProductoAdapter(private val detalleProductos:List<DetalleProducto>,
                             private val onClickListenerAdd: (DetalleProducto) -> Unit,
                            private val onClickListenerDelete: (DetalleProducto) -> Unit,
                             private val onClickListenerDisminuir: (DetalleProducto) -> Unit)
    : RecyclerView.Adapter<DetalleProductoViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetalleProductoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DetalleProductoViewHolder(layoutInflater.inflate(R.layout.item_producto, parent, false))
    }

    override fun getItemCount(): Int {
        return detalleProductos.size
    }

    override fun onBindViewHolder(holder: DetalleProductoViewHolder, position: Int) {
        val item = detalleProductos[position]
        holder.render(item, onClickListenerAdd, onClickListenerDelete, onClickListenerDisminuir)
    }
}