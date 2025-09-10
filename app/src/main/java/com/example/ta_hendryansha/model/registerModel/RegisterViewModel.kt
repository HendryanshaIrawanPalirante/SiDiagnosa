package com.example.ta_hendryansha.model.registerModel

import androidx.lifecycle.ViewModel
import com.example.ta_hendryansha.data.repository.UserRepository

class RegisterViewModel(private val repository: UserRepository): ViewModel() {
    fun registerUser(
        noBpjs: Int,
        email: String,
        password:String,
        namaLengkap:String,
        umur: Int,
        jenisKelamin: String,
        noKartuBerobat: String,
        tipeAkun: String
    ) = repository.registerUser(noBpjs, email, password, namaLengkap, umur, jenisKelamin, noKartuBerobat, tipeAkun)
}