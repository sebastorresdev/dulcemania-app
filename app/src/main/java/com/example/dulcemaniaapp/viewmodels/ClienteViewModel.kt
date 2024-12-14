package com.example.dulcemaniaapp.viewmodels

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ClienteViewModel: ViewModel() {
    val clienteSeleccionado = MutableLiveData<Boolean>()
    val direccionSeleccionada = MutableLiveData<Boolean>()

}