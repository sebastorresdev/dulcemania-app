package com.example.dulcemaniaapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dulcemaniaapp.R
import com.example.dulcemaniaapp.models.ItemProducto
import com.example.dulcemaniaapp.viewmodels.ProductoViewModel

class ProductoAdapter(private val productos: MutableList<ItemProducto>, private val productoViewModel: ProductoViewModel) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    // Clase interna para representar cada tarjeta
    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombreProducto: TextView = itemView.findViewById(R.id.tvProductoNombre)
        val tvCantidadProducto: TextView = itemView.findViewById(R.id.tvProductoCantidad)
        val tvProductoID : TextView = itemView.findViewById(R.id.tvProductoID)
        val btnEliminarProducto : ImageButton = itemView.findViewById(R.id.btnEliminarProducto)
        val btnAgregarProducto : ImageButton = itemView.findViewById(R.id.btnAgregarCantidad)

        fun bind(itemProducto: ItemProducto) {
            tvNombreProducto.text = itemProducto.nombre
            tvCantidadProducto.text = "Cantidad: ${itemProducto.cantidad}"
            tvProductoID.text = "ID: ${itemProducto.id}"

            btnEliminarProducto.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Eliminar el producto de la lista
                    //productos.removeAt(position)

                    // Notificar al ViewModel que se eliminó un producto
                    productoViewModel.eliminarProducto(productos, position)

                    // Notificar al RecyclerView que el elemento fue eliminado
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, productos.size)
                }
            }

            // Evento para el botón Agregar cantidad
            btnAgregarProducto.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Aumenta la cantidad del producto
                    itemProducto.cantidad += 1
                    // Actualiza el TextView de la cantidad
                    tvCantidadProducto.text = "Cantidad: ${itemProducto.cantidad}"
                    productoViewModel.actualizarCantidad(productos, position, itemProducto.cantidad)
                    notifyItemChanged(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.producto_item, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val itemProducto = productos[position]
        holder.bind(itemProducto)
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    // Método para agregar un producto y notificar al RecyclerView
    fun agregarProducto(itemProducto: ItemProducto) {
        productos.add(itemProducto)
        notifyItemInserted(productos.size - 1)
    }
}