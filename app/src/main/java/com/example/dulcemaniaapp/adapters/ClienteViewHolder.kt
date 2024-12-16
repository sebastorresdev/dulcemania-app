package com.example.dulcemaniaapp.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.dulcemaniaapp.databinding.ItemClienteBinding
import com.example.dulcemaniaapp.models.Cliente

class ClienteViewHolder(view: View): ViewHolder(view) {
    val binding = ItemClienteBinding.bind(view)

    fun render(cliente: Cliente) {
        binding.textViewClientName.text = cliente.razonSocial
        binding.textViewClientEmail.text = cliente.correo
        binding.textViewClientPhone.text = "Telefono: ${cliente.telefono}"
        binding.textViewRuc.text = "RUC: ${cliente.ruc}"
    }
}