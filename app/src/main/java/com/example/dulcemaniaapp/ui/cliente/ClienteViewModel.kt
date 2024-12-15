package com.example.dulcemaniaapp.ui.cliente

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dulcemaniaapp.models.Cliente
import com.example.dulcemaniaapp.models.Direccion

class ClienteViewModel: ViewModel() {
    val clienteSeleccionado = MutableLiveData<Cliente>()
    val direccionSeleccionada = MutableLiveData<Direccion>()

}