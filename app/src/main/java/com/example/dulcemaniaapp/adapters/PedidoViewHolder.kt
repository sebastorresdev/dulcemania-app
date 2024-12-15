package com.example.dulcemaniaapp.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.dulcemaniaapp.databinding.ItemPedidoBinding
import com.example.dulcemaniaapp.models.Pedido

class PedidoViewHolder(view: View): ViewHolder(view) {

    val binding = ItemPedidoBinding.bind(view)

    fun render(pedido: Pedido) {
        binding.tipoDocumento.text = "${pedido.tipoDocumento} - RUC: ${pedido.ruc}"
        binding.nombreCliente.text = "Cliente: ${pedido.nombreCliente}"
        binding.estado.text = "Estado: ${pedido.estado}"
        binding.total.text = "Total: S/${pedido.total}"
    }
}