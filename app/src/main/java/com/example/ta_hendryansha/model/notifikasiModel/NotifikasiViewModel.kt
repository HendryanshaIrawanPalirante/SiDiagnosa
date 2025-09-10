package com.example.ta_hendryansha.model.notifikasiModel

import androidx.lifecycle.ViewModel
import com.example.ta_hendryansha.data.repository.UserRepository

class NotifikasiViewModel(private val repository: UserRepository): ViewModel() {

    fun getDetail(idDetail: String) = repository.getDetailGejala(idDetail)

}