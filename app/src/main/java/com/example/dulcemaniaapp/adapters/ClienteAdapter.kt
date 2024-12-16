package com.example.dulcemaniaapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dulcemaniaapp.R
import com.example.dulcemaniaapp.models.Cliente

class ClienteAdapter(private val clientes:List<Cliente>) : RecyclerView.Adapter<ClienteViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return ClienteViewHolder(layoutInflater.inflate(R.layout.item_cliente, parent, false))
        }

        override fun getItemCount(): Int {
            return clientes.size
        }

        override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
            val item = clientes[position]
            holder.render(item)
        }
}