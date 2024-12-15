package com.example.dulcemaniaapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dulcemaniaapp.R
import com.example.dulcemaniaapp.models.Pedido

class PedidoAdapter(private val pedidos:List<Pedido>): RecyclerView.Adapter<PedidoViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PedidoViewHolder(layoutInflater.inflate(R.layout.item_pedido, parent, false))
    }

    override fun getItemCount(): Int {
        return pedidos.size
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val item = pedidos[position]
        holder.render(item)
    }

}