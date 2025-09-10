package com.example.ta_hendryansha.model.biodataModel

import androidx.lifecycle.ViewModel
import com.example.ta_hendryansha.data.repository.UserRepository

class BiodataUserViewModel(private val repository: UserRepository): ViewModel() {

    fun getUser(email: String) = repository.getUser(email)
    fun updateUser(
        idEmail: String,
        idBpjs: Int,
        noBpjs: Int,
        email: String,
        password: String,
        namaLengkap: String,
        umur: Int,
        jenis_kelamin: String,
        noKartuBerobat: String) = repository.updateUser(idEmail, idBpjs, noBpjs, email, password, namaLengkap, umur, jenis_kelamin, noKartuBerobat)

}