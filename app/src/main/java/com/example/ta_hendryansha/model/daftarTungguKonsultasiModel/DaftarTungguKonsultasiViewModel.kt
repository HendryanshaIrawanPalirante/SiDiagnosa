package com.example.ta_hendryansha.model.daftarTungguKonsultasiModel

import androidx.lifecycle.ViewModel
import com.example.ta_hendryansha.data.repository.UserRepository

class DaftarTungguKonsultasiViewModel(private val repository: UserRepository): ViewModel() {

    fun getListKonsultasi(idUser: Int) = repository.getDaftarKonsultasi(idUser)
    fun getUser(email: String) = repository.getUser(email)

}