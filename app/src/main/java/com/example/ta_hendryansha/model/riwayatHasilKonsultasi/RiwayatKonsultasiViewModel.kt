package com.example.ta_hendryansha.model.riwayatHasilKonsultasi

import androidx.lifecycle.ViewModel
import com.example.ta_hendryansha.data.repository.UserRepository

class RiwayatKonsultasiViewModel(private val repository: UserRepository): ViewModel() {

    fun getRiwayatHasilKonsultasi() = repository.getRiwayatHasilKonsultasi()

}