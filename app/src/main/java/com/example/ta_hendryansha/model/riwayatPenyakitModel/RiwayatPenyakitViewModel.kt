package com.example.ta_hendryansha.model.riwayatPenyakitModel

import androidx.lifecycle.ViewModel
import com.example.ta_hendryansha.data.repository.UserRepository

class RiwayatPenyakitViewModel(private val repository: UserRepository): ViewModel() {

    fun getRiwayatPenyakit(noBpjs: Int) = repository.getRiwayatPenyakit(noBpjs)

}