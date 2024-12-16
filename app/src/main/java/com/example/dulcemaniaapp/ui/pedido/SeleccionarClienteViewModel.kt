package com.example.dulcemaniaapp.ui.pedido

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dulcemaniaapp.models.Cliente
import com.example.dulcemaniaapp.models.Direccion

class SeleccionarClienteViewModel : ViewModel() {
    val clienteSeleccionado = MutableLiveData<Cliente>()
    val direccionSeleccionada = MutableLiveData<Direccion>()

}