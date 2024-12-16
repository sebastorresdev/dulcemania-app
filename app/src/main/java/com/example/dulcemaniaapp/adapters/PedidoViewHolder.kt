package com.example.dulcemaniaapp.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.dulcemaniaapp.databinding.ItemPedidoBinding
import com.example.dulcemaniaapp.models.Pedido

class PedidoViewHolder(view: View): ViewHolder(view) {

    val binding = ItemPedidoBinding.bind(view)

    fun render(pedido: Pedido, onClickListenerVerDetalle: (Pedido) -> Unit,onClickListenerDelete: (Pedido) -> Unit) {
        binding.tipoDocumento.text = "${pedido.tipoDocumento} - RUC: ${pedido.ruc}"
        binding.nombreCliente.text = "Cliente: ${pedido.nombreCliente}"
        binding.estado.text = "Estado: ${pedido.estado}"
        binding.total.text = "Total: S/${pedido.total}"
        binding.btnEliminar.visibility = if (pedido.estado == "pendiente") View.VISIBLE else View.GONE

        binding.btnVerDetalle.setOnClickListener { onClickListenerVerDetalle(pedido) }
        binding.btnEliminar.setOnClickListener { onClickListenerDelete(pedido) }
    }
}