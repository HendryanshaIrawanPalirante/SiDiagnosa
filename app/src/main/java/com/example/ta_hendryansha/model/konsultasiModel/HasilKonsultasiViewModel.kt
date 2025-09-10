package com.example.ta_hendryansha.model.konsultasiModel

import androidx.lifecycle.ViewModel
import com.example.ta_hendryansha.data.repository.UserRepository

class HasilKonsultasiViewModel(private val repository: UserRepository): ViewModel() {

    fun ruleCf(kodePenyakit: String) = repository.ruleCf(kodePenyakit)
    fun getPenyakit(kodePenyakit: String) = repository.getPenyakit(kodePenyakit)
    fun getUser(email: String) = repository.getUser(email)

}