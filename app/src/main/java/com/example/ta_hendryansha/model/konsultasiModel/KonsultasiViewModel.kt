package com.example.ta_hendryansha.model.konsultasiModel

import androidx.lifecycle.ViewModel
import com.example.ta_hendryansha.data.repository.UserRepository

class KonsultasiViewModel(private val repository: UserRepository): ViewModel() {

    fun pertanyaan(kodeGejala: String) = repository.pertanyaan(kodeGejala)
    fun ruleFc(kodeGejala: String, action: String) = repository.ruleFc(kodeGejala, action)

}